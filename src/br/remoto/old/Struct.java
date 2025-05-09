package br.remoto.old;

/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2005 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
import java.lang.UnsupportedOperationException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.lang.Enum;


/**
 * <p> This class represents a <code>C/C++ struct</code>; it confers
 *     interoperability between Java classes and C/C++ struct.</p>
 * <p> Unlike <code>C/C++</code>, the storage layout of Java objects is not
 *     determined by the compiler. The layout of objects in memory is deferred
 *     to run time and determined by the interpreter (or just-in-time compiler).
 *     This approach allows for dynamic loading and binding; but also makes
 *     interfacing with <code>C/C++</code> code difficult. Hence, this class for
 *     which the memory layout is defined by the initialization order of the
 *     {@link Struct}'s {@link Member members} and follows the same alignment
 *      rules as <code>C/C++ structs</code>.</p>
 * <p> This class (as well as the {@link Union} sub-class) facilitates:
 *     <ul>
 *     <li> Memory sharing between Java applications and native libraries.</li>
 *     <li> Direct encoding/decoding of streams for which the structure
 *          is defined by legacy C/C++ code.</li>
 *     <li> Serialization/deserialization of Java objects (complete control,
 *          e.g. no class header)</li>
 *     <li> Mapping of Java objects to physical addresses (with JNI).</li>
 *     </ul></p>
 * <p> Because of its one-to-one mapping, it is relatively easy to convert C 
 *     header files (e.g. OpenGL bindings) to Java {@link Struct}/{@link Union}
 *     using simple text macros. Here is an example of C struct:<pre>
 *     struct Date {
 *         unsigned short year;
 *         unsigned char month;
 *         unsigned char day;
 *     };
 *     struct Student {
 *         char        name[64];
 *         struct Date birth;
 *         float       grades[10];
 *         Student*    next;
 *     };</pre>
 *     and here is the Java equivalent using this class:<pre>
 *     public static class Date extends Struct {
 *         public final Unsigned16 year = new Unsigned16();
 *         public final Unsigned8 month = new Unsigned8();
 *         public final Unsigned8 day   = new Unsigned8();
 *     }
 *     public static class Student extends Struct {
 *         public final Utf8String  name   = new Utf8String(64);
 *         public final Date        birth  = (Date) inner(new Date());
 *         public final Float32[]   grades = (Float32[]) array(new Float32[10]);
 *         public final Reference32 next   =  new Reference32(Student.class);
 *     }</pre>
 *     Struct's members are directly accessible:<pre>
 *     Student student = new Student();
 *     student.name.set("John Doe"); // Null terminated (C compatible)
 *     int age = 2003 - student.birth.year.get();
 *     student.grades[2].set(12.5f);
 *     student = (Student) student.next.get();</pre></p>
 * <p> Applications may also work with the raw {@link #getByteBuffer() bytes}
 *     directly. The following illustrate how {@link Struct} can be used to 
 *     decode/encode UDP messages directly:<pre>
 *     class MyUdpMessage extends Struct {
 *         ... // UDP message fields.
 *     }
 *     public void run() {
 *         byte[] bytes = new byte[1024];
 *         DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
 *         MyUdpMessage message = new MyUdpMessage();
 *         message.setByteBuffer(ByteBuffer.wrap(bytes), 0);
 *         // packet and message are now two different views of the same data. 
 *         while (true) {
 *             _socket.receive(packet);
 *             ... // Process message fields directly.
 *             packet.setLength(bytes.length); // Reset length to buffer's length.
 *         }
 *     }</pre></p> 
 * <p> It is relatively easy to map instances of this class to any physical
 *     address using
 *     <a href="http://java.sun.com/docs/books/tutorial/native1.1/index.html">
 *     JNI</a>. Here is an example:<pre>
 *     import java.nio.ByteBuffer;
 *     class Clock extends Struct { // Hardware clock mapped to memory.
 *         Unsigned16 seconds  = new Unsigned16(5); // unsigned short seconds:5
 *         Unsigned16 minutes  = new Unsigned16(5); // unsigned short minutes:5
 *         Unsigned16 hours    = new Unsigned16(4); // unsigned short hours:4
 *         Clock() {
 *             setByteBuffer(Clock.nativeBuffer(), 0);
 *         }
 *         private static native ByteBuffer nativeBuffer();
 *     }</pre>
 *     Below is the <code>nativeBuffer()</code> implementation
 *     (<code>Clock.c</code>):<pre>
 *     #include &lt;jni.h&gt;
 *     #include "Clock.h" // Generated using javah
 *     JNIEXPORT jobject JNICALL Java_Clock_nativeBuffer (JNIEnv *env, jclass) {
 *         return (*env)->NewDirectByteBuffer(env, clock_address, buffer_size)
 *     }</pre></p>
 * <p> Bit-fields are supported (see <code>Clock</code> example above).
 *     Bit-fields allocation order is defined by the Struct {@link #byteOrder}
 *     return value (leftmost bit to rightmost bit if
 *     <code>BIG_ENDIAN</code> and rightmost bit to leftmost bit if
 *      <code>LITTLE_ENDIAN</code>).
 *     Unless the Struct {@link #isPacked packing} directive is overriden,
 *     bit-fields cannot straddle the storage-unit boundary as defined by their
 *     base type (padding is inserted at the end of the first bit-field
 *     and the second bit-field is put into the next storage unit).</p>
 * <p> Finally, it is possible to {@link #setByteBuffer change} the {@link 
 *     ByteBuffer} or the {@link Struct}'s position in the {@link ByteBuffer}
 *     to allow for a single {@link Struct} object to encode/decode multiple
 *     memory mapped instances.</p>
 * 
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 2.2, February 4, 2005
 */
public class Struct {

    /**
     * Holds the outer struct if any.
     */
    private Struct _outer;

    /**
     * Holds the byte buffer backing the struct (top struct).
     */
    private ByteBuffer _byteBuffer;

    /**
     * Holds the offset of this struct relative to the outer struct or
     * to the byte buffer if there is no outer.
     */
    private int _outerOffset;

    /**
     * Holds the number of bits currently used (for size calculation).
     */
    private int _bitsUsed;

    /**
     * Holds this struct alignment (largest alignment of its members).
     */
    private int _alignment;

    /**
     * Holds the current bit index position (during construction).
     */
    private int _bitIndex;

    /**
     * Indicates if the index has to be reset for each new field.
     */
    private boolean _resetIndex;

    /**
     * Default constructor.
     */
    public Struct() {
        _resetIndex = (this instanceof Union);
    }

    /**
     * Returns the size in bytes of this {@link Struct}. The size includes
     * tail padding to satisfy the {@link Struct} alignment requirement
     * (defined by the largest alignment of its {@link Member members}).
     *
     * @return the C/C++ <code>sizeof(this)</code>.
     */
    public final int size() {
        int nbrOfBytes = (_bitsUsed + 7) >> 3;
        return ((nbrOfBytes % _alignment) == 0) ? nbrOfBytes : // Already aligned or packed.
                nbrOfBytes + _alignment - (nbrOfBytes % _alignment); // Tail padding.
    }

    /**
     * Returns the {@link ByteBuffer} for this {@link Struct}.
     * This method will allocate a new buffer if none has been set.
     *
     * <p> Changes to the buffer's content are visible in this {@link Struct},
     *     and vice versa.</p>
     * <p> The buffer of an inner {@link Struct} is the same as its parent
     *     {@link Struct}.</p>
     * <p> The position of a {@link Struct.Member struct's member} within the 
     *     byte buffer is given by {@link Struct.Member#position
     *     member.position()}</p>
     *
     * @return the current byte buffer or a new direct buffer if none set.
     * @see #setByteBuffer
     */
    public final ByteBuffer getByteBuffer() {
        if (_outer != null)
            return _outer.getByteBuffer();
        return (_byteBuffer != null) ? _byteBuffer : newBuffer();
    }

    private ByteBuffer newBuffer() {
        int size = size();
        // Covers misaligned 64 bits access when packed.
        int capacity = isPacked() ? (((size & 0x7) == 0) ? size : size + 8
                - (size & 0x7)) : size;
        ByteBuffer bf = ByteBuffer.allocateDirect(capacity);
        bf.order(byteOrder());
        setByteBuffer(bf, 0);
        return _byteBuffer;
    }

    /**
     * Sets the current {@link ByteBuffer} for this {@link Struct}.
     * The specified byte buffer can be mapped to memory for direct memory
     * access or can wrap a shared byte array for I/O purpose 
     * (e.g. <code>DatagramPacket</code>).
     *
     * @param byteBuffer the new byte buffer.
     * @param position the position of this struct in the specified byte buffer.
     * @throws UnsupportedOperationException if this struct is an inner struct. 
     */
    public final void setByteBuffer(ByteBuffer byteBuffer, int position) {
        if (_outer != null)
            throw new UnsupportedOperationException(
                    "Inner struct byte buffer is inherited from outer");
        _byteBuffer = byteBuffer;
        _outerOffset = position; //
    }

    /**
     * Returns the absolute position of this struct within its associated 
     * {@link #getByteBuffer byte buffer}.
     *
     * @return the absolute position of this struct in the byte buffer. 
     */
    public final int getByteBufferPosition() {
        return (_outer != null) ? _outer.getByteBufferPosition() + _outerOffset
                : _outerOffset;
    }

    /**
     * Returns this {@link Struct} address. This method allows for 
     * {@link Struct} to be referenced (e.g. pointer) from other {@link Struct}.
     *
     * @return the struct memory address.
     * @throws UnsupportedOperationException if the struct's buffer is not 
     *         a direct buffer.
     * @see    Reference32
     * @see    Reference64
     */
    public final long address() {
        ByteBuffer thisBuffer = this.getByteBuffer();
        if (false) {
            long start = 0; // sun.nio.ch.DirectBuffer.address();
            return start + getByteBufferPosition();
        } else {
            throw new UnsupportedOperationException(
                    "Operation not supported for " + thisBuffer.getClass());
        }
    }


    /**
     * Returns the <code>String</code> representation of this {@link Struct}
     * in the form of its constituing bytes (hexadecimal). For example:<pre>
     *     public static class Student extends Struct {
     *         Utf8String name  = new Utf8String(16);
     *         Unsigned16 year  = new Unsigned16();
     *         Float32    grade = new Float32();
     *     }
     *     Student student = new Student();
     *     student.name.set("John Doe");
     *     student.year.set(2003);
     *     student.grade.set(12.5f);
     *     System.out.println(student);
     *
     *     4A 6F 68 6E 20 44 6F 65 00 00 00 00 00 00 00 00
     *     07 D3 00 00 41 48 00 00</pre>
     *
     * @return a hexadecimal representation of the bytes content for this
     *         {@link Struct}.
     */
    public String toString() {
        final int size = size();
        StringBuffer sb = new StringBuffer(size * 3);
        final ByteBuffer buffer = getByteBuffer();
        final int start = getByteBufferPosition();
        for (int i = 0; i < size; i++) {
            int b = buffer.get(start + i) & 0xFF;
            sb.append(HEXA[b >> 4]);
            sb.append(HEXA[b & 0xF]);
            sb.append(((i & 0xF) == 0xF) ? '\n' : ' ');
        }
        return sb.toString();
    }

    private static final char[] HEXA = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    ///////////////////
    // CONFIGURATION //
    ///////////////////

    /**
     * Returns the byte order for this {@link Struct}. The byte order is
     * inherited by inner structs. Sub-classes may change the byte order
     * by overriding this method. For example:<pre>
     * public class TopStruct extends Struct {
     *     ... // Members initialization.
     *     public ByteOrder byteOrder() {
     *         // TopStruct and its inner structs use hardware byte order.
     *         return ByteOrder.nativeOrder();
     *    }
     * }}</pre></p></p>
     *
     * @return the byte order when reading/writing multibyte values
     *         (default: network byte order, <code>BIG_ENDIAN</code>).
     */
    public ByteOrder byteOrder() {
        return (_outer != null) ? _outer.byteOrder() : ByteOrder.BIG_ENDIAN;
    }

    /**
     * Indicates if this {@link Struct} is packed.
     * By default, {@link Member} of a {@link Struct} are aligned on the
     * boundary corresponding to the member's base type; padding is performed
     * if necessary. This directive is inherited by inner structs.
     * Sub-classes may change the packing directive by overriding this method.
     * For example:<pre>
     * public class TopStruct extends Struct {
     *     ... // Members initialization.
     *     public boolean isPacked() {
     *         // TopStruct and its inner structs are packed.
     *         return true;
     *     }
     * }}</pre></p></p>
     *
     * @return <code>true</code> if alignment requirements are ignored.
     *         <code>false</code> otherwise (default).
     */
    public boolean isPacked() {
        return (_outer != null) ? _outer.isPacked() : false;
    }

    /**
     * Allocates the specified struct as inner of this struct.
     *
     * @param struct the inner struct.
     * @return the specified struct. 
     * @throws IllegalArgumentException if the specified struct is already 
     *         an inner struct.
     */
    public final Struct inner(Struct struct) {
        if (struct._outer != null)
            throw new IllegalArgumentException(
                    "struct: Already an inner struct");
        struct._outer = this;
        final int bitSize = struct.size() << 3;
        updateIndexes(struct._alignment, bitSize, bitSize);
        struct._outerOffset = (_bitIndex - bitSize) >> 3;
        return struct;
    }

    /**
     * Allocates the specified array of structs as inner structs. 
     * The array is populated if necessary using the struct component
     * default constructor (which must be public).
     *
     * @param structs the struct array.
     * @return the specified struct array. 
     * @throws IllegalArgumentException if the specified array contains 
     *         inner structs.
     */
    public final Struct[] array(Struct[] structs) {
        Class structClass = null;
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        for (int i = 0; i < structs.length;) {
            Struct struct = structs[i];
            if (struct == null) {
                try {
                    if (structClass == null) {
                        String arrayName = structs.getClass().getName();
                        String structName = arrayName.substring(2, arrayName
                                .length() - 1);
                        structClass = Class.forName(structName);
                    }
                    struct = (Struct) structClass.newInstance();
                } catch (Exception e) {
                    throw new Error(e);
                }
            }
            structs[i++] = inner(struct);
        }
        _resetIndex = resetIndexSaved;
        return structs;
    }

    /**
     * Allocates the specified two-dimensional array of structs as inner
     * structs. The array is populated if necessary using the struct component 
     * default constructor (which must be public).
     *
     * @param structs the two dimensional struct array.
     * @return the specified struct array. 
     * @throws IllegalArgumentException if the specified array contains 
     *         inner structs.
     */
    public final Struct[][] array(Struct[][] structs) 
    {
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        for (int i = 0; i < structs.length; i++) {
            array(structs[i]);
        }
        _resetIndex = resetIndexSaved;
        return structs;
    }

    /**
     * Allocates the specified three dimensional array of structs as inner 
     * structs. The array is populated if necessary using the struct component 
     * default constructor (which must be public).
     *
     * @param structs the three dimensional struct array.
     * @return the specified struct array.
     * @throws IllegalArgumentException if the specified array contains 
     *         inner structs.
     */
    public final Struct[][][] array(Struct[][][] structs) 
    {
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        for (int i = 0; i < structs.length; i++) {
            array(structs[i]);
        }
        _resetIndex = resetIndexSaved;
        return structs;
    }

    /**
     * Allocates the specified array member. For predefined members, 
     * the array is populated when empty; custom members should use 
     * literal (populated) arrays.
     *
     * @param  arrayMember the array member.
     * @return the specified array member.
     * @throws UnsupportedOperationException if the specified array 
     *         is empty and the member type is unknown. 
     */
    public final Member[] array(Member[] arrayMember) {
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        if (BOOL.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Bool();
        } else if (SIGNED_8.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Signed8();
        } else if (UNSIGNED_8.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Unsigned8();
        } else if (SIGNED_16.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Signed16();
        } else if (UNSIGNED_16.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Unsigned16();
        } else if (SIGNED_32.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Signed32();
        } else if (UNSIGNED_32.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Unsigned32();
        } else if (SIGNED_64.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Signed64();
        } else if (FLOAT_32.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Float32();
        } else if (FLOAT_64.isInstance(arrayMember)) {
            for (int i = 0; i < arrayMember.length;)
                arrayMember[i++] = this.new Float64();
        } else {
            throw new UnsupportedOperationException(
                    "Unknown member, literal array should be used");
        }
        _resetIndex = resetIndexSaved;
        return arrayMember;
    }

    private static final Class BOOL = new Bool[0].getClass();

    private static final Class SIGNED_8 = new Signed8[0].getClass();

    private static final Class UNSIGNED_8 = new Unsigned8[0].getClass();

    private static final Class SIGNED_16 = new Signed16[0].getClass();

    private static final Class UNSIGNED_16 = new Unsigned16[0].getClass();

    private static final Class SIGNED_32 = new Signed32[0].getClass();

    private static final Class UNSIGNED_32 = new Unsigned32[0].getClass();

    private static final Class SIGNED_64 = new Signed64[0].getClass();

    private static final Class FLOAT_32 = new Float32[0].getClass();

    private static final Class FLOAT_64 = new Float64[0].getClass();

    /**
     * Allocates the specified two-dimensional array member. For predefined
     * members, the array is populated when empty; custom members should use 
     * literal (populated) arrays.
     *
     * @param  arrayMember the two-dimensional array member.
     * @return the specified array member.
     * @throws UnsupportedOperationException if the specified array 
     *         is empty and the member type is unknown. 
     */
    public final Member[][] array(Member[][] arrayMember) {
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        for (int i = 0; i < arrayMember.length; i++) {
            array(arrayMember[i]);
        }
        _resetIndex = resetIndexSaved;
        return arrayMember;
    }

    /**
     * Allocates the specified three-dimensional array member. For predefined
     * members, the array is populated when empty; custom members should use 
     * literal (populated) arrays.
     *
     * @param  arrayMember the three-dimensional array member.
     * @return the specified array member.
     * @throws UnsupportedOperationException if the specified array 
     *         is empty and the member type is unknown. 
     */
    public final Member[][][] array(Member[][][] arrayMember) {
        boolean resetIndexSaved = _resetIndex;
        if (_resetIndex) {
            _bitIndex = 0;
            _resetIndex = false; // Ensures the array elements are sequential.
        }
        for (int i = 0; i < arrayMember.length; i++) {
            array(arrayMember[i]);
        }
        _resetIndex = resetIndexSaved;
        return arrayMember;
    }

    /**
     * Updates this struct indexes after adding a member with the 
     * specified constraints.
     *
     * @param  alignment  the desired alignment in bytes.
     * @param  nbrOfBits  the size in bits.
     * @param  capacity   the word size maximum capacity in bits
     *                    (equal to nbrOfBits for non-bitfields).
     * @return offset the offset of the member.
     * @throws IllegalArgumentException if
     *         <code>nbrOfBits &gt; capacity</code>
     */
    private int updateIndexes(int alignment, int nbrOfBits, int capacity) {
        if (nbrOfBits > capacity) {
            throw new IllegalArgumentException("nbrOfBits: " + nbrOfBits
                    + " exceeds capacity: " + capacity);
        }

        // Resets index if union.
        if (_resetIndex) {
            _bitIndex = 0;
        }

        // Caculates offset based on alignment constraints.
        alignment = isPacked() ? 1 : alignment;
        int offset = (_bitIndex / (alignment << 3)) * alignment;

        // Calculates bits already used from the offset position.
        int usedBits = _bitIndex - (offset << 3);

        // Checks if bits can be adjacents. 
        // A bit size of 0 forces realignment, ref. C/C++ Standard.
        if ((capacity < usedBits + nbrOfBits)
                || ((nbrOfBits == 0) && (usedBits != 0))) {
            // Padding to next alignment boundary.
            offset += alignment;
            _bitIndex = (offset << 3) + nbrOfBits;
        } else { // Adjacent bits.
            _bitIndex += nbrOfBits;
        }

        // Updates bits used (for size calculation).
        if (_bitsUsed < _bitIndex) {
            _bitsUsed = _bitIndex;
        }

        // Updates Struct's alignment.
        if (_alignment < alignment) {
            _alignment = alignment;
        }
        return offset;
    }

    /////////////
    // MEMBERS //
    /////////////

    /**
     * This inner class represents the base class for all {@link Struct}
     * members. It allows applications to define additional member types.
     * For example:<pre>
     *    public class MyStruct extends Struct {
     *        BitSet bits = new BitSet(256);
     *        ...
     *        public BitSet extends Member {
     *            public BitSet(int nbrBits) {
     *                super(1, (nbrBits+7)>>3);
     *            }
     *            public boolean get(int i) { ... }
     *            public void set(int i, boolean value) { ...}
     *        }
     *    }</pre>
     */
    protected class Member {

        /**
         * Holds the relative offset of this member within its struct.
         */
        private final int _offset;

        /**
         * Base constructor for custom member types.
         *
         * @param  alignment the desired alignment in bytes.
         * @param  size the size of this member in bytes.
         */
        protected Member(int alignment, int size) {
            final int nbrOfBits = size << 3;
            _offset = updateIndexes(alignment, nbrOfBits, nbrOfBits);
        }

        /**
         * Base constructor for predefined member types with bit fields.
         *
         * @param  alignment  the desired alignment in bytes.
         * @param  nbrOfBits  the size in bits.
         * @param  capacity   the word size maximum capacity in bits
         *                    (equal to nbrOfBits for non-bitfields).
         */
        Member(int alignment, int nbrOfBits, int capacity) {
            _offset = updateIndexes(alignment, nbrOfBits, capacity);
        }

        /**
         * Returns the relative offset of this member within its struct.
         *
         * @return the relative offset in bytes.
         */
        public final int offset() {
            return _offset;
        }

        /**
         * Returns the absolute position of this member in the 
         * byte buffer.
         *
         * @return the byte buffer position.
         */
        public final int position() {
            return getByteBufferPosition() + _offset;
        }
    }

    ///////////////////////
    // PREDEFINED FIELDS //
    ///////////////////////

    /**
     * This class represents a 8 bits boolean with <code>true</code> represented
     * by <code>1</code> and <code>false</code> represented by <code>0</code>.
     */
    public class Bool extends Member {
        private final int _mask;

        private final int _shift;

        public Bool() {
            this(8);
        }

        public Bool(int nbrOfBits) {
            super(1, nbrOfBits, 8);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 8 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
        }

        public boolean get() {
            return (getByteBuffer().get(position()) & _mask) != 0;
        }

        public void set(boolean value) {
            if (_mask == 0xFF) { // Non bit-field.
                getByteBuffer().put(position(), (byte) (value ? 1 : 0));
            } else { // Bit-field.
                int prevCleared = getByteBuffer().get(position()) & (~_mask);
                if (value) {
                    getByteBuffer().put(position(),
                            (byte) (prevCleared | (1 << _shift)));
                } else {
                    getByteBuffer().put(position(), (byte) (prevCleared));
                }
            }
        }
    }

    /**
     * This class represents a 8 bits signed integer.
     */
    public class Signed8 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        public Signed8() {
            this(8);
        }

        public Signed8(int nbrOfBits) {
            super(1, nbrOfBits, 8);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 8 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public byte get() {
            if (_mask == 0xFF) { // Non bit-field.
                return getByteBuffer().get(position());
            } else { // Bit-field.
                int value = getByteBuffer().get(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (byte) value;
            }
        }

        public void set(byte value) {
            if (_mask == 0xFF) { // Non bit-field.
                getByteBuffer().put(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().get(position()) & (~_mask);
                getByteBuffer().put(position(), (byte) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 8 bits unsigned integer.
     */
    public class Unsigned8 extends Member {
        private final int _shift;

        private final int _mask;

        public Unsigned8() {
            this(8);
        }

        public Unsigned8(int nbrOfBits) {
            super(1, nbrOfBits, 8);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 8 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
        }

        public short get() {
            int value = getByteBuffer().get(position());
            return (short) ((value & _mask) >>> _shift);
        }

        public void set(short value) {
            if (_mask == 0xFF) { // Non bit-field.
                getByteBuffer().put(position(), (byte) value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().get(position()) & (~_mask);
                getByteBuffer().put(position(), (byte) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 16 bits signed integer.
     */
    public class Signed16 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        public Signed16() {
            this(16);
        }

        public Signed16(int nbrOfBits) {
            super(2, nbrOfBits, 16);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 16 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public short get() {
            if (_mask == 0xFFFF) { // Non bit-field.
                return getByteBuffer().getShort(position());
            } else { // Bit-field.
                int value = getByteBuffer().getShort(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (short) value;
            }
        }

        public void set(short value) {
            if (_mask == 0xFFFF) { // Non bit-field.
                getByteBuffer().putShort(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getShort(position()) & (~_mask);
                getByteBuffer().putShort(position(), (short) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 16 bits unsigned integer.
     */
    public class Unsigned16 extends Member {
        private final int _shift;

        private final int _mask;

        public Unsigned16() {
            this(16);
        }

        public Unsigned16(int nbrOfBits) {
            super(2, nbrOfBits, 16);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 16 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
        }

        public int get() {
            int value = getByteBuffer().getShort(position());
            return (value & _mask) >>> _shift;
        }

        public void set(int value) {
            if (_mask == 0xFFFF) { // Non bit-field.
                getByteBuffer().putShort(position(), (short) value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getShort(position()) & (~_mask);
                getByteBuffer().putShort(position(), (short) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 32 bits signed integer.
     */
    public class Signed32 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        public Signed32() {
            this(32);
        }

        public Signed32(int nbrOfBits) {
            super(4, nbrOfBits, 32);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 32 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = (nbrOfBits == 32) ? 0xFFFFFFFF
                    : ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public int get() {
            if (_mask == 0xFFFFFFFF) { // Non bit-field.
                return getByteBuffer().getInt(position());
            } else { // Bit-field.
                int value = getByteBuffer().getInt(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return value;
            }
        }

        public void set(int value) {
            if (_mask == 0xFFFFFFFF) { // Non bit-field.
                getByteBuffer().putInt(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getInt(position()) & (~_mask);
                getByteBuffer().putInt(position(), orMask | value);
            }
        }
    }

    /**
     * This class represents a 32 bits unsigned integer.
     */
    public class Unsigned32 extends Member {
        private final int _shift;

        private final long _mask;

        public Unsigned32() {
            this(32);
        }

        public Unsigned32(int nbrOfBits) {
            super(4, nbrOfBits, 32);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 32 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = (nbrOfBits == 32) ? 0xFFFFFFFFl
                    : ((1l << nbrOfBits) - 1l) << _shift;
        }

        public long get() {
            int value = getByteBuffer().getInt(position());
            return (value & _mask) >>> _shift;
        }

        public void set(long value) {
            if (_mask == 0xFFFFFFFF) { // Non bit-field.
                getByteBuffer().putInt(position(), (int) value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getInt(position())
                        & (~(int) _mask);
                getByteBuffer().putInt(position(), (int) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 64 bits signed integer.
     */
    public class Signed64 extends Member {
        private final long _mask;

        private final int _shift;

        private final int _signShift;

        public Signed64() {
            this(64);
        }

        public Signed64(int nbrOfBits) {
            super(8, nbrOfBits, 64);
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 64 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = (nbrOfBits == 64) ? 0xFFFFFFFFFFFFFFFFl
                    : ((1l << nbrOfBits) - 1l) << _shift;
            _signShift = 64 - _shift - nbrOfBits;
        }

        public long get() {
            if (_mask == 0xFFFFFFFFFFFFFFFFl) { // Non bit-field.
                return getByteBuffer().getLong(position());
            } else { // Bit-field.
                long value = getByteBuffer().getLong(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return value;
            }
        }

        public void set(long value) {
            if (_mask == 0xFFFFFFFFFFFFFFFFl) { // Non bit-field.
                getByteBuffer().putLong(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                long orMask = getByteBuffer().getLong(position()) & (~_mask);
                getByteBuffer().putLong(position(), orMask | value);
            }
        }
    }

    /**
     * This class represents a 32 bits float (C/C++/Java <code>float</code>).
     */
    public class Float32 extends Member {
        public Float32() {
            super(4, 4);
        }
        /**/
         public void set(float value) {
         getByteBuffer().putFloat(position(), value);
         }

         public float get() {
         return getByteBuffer().getFloat(position());
         }
         /**/
    }

    /**
     * This class represents a 64 bits float (C/C++/Java <code>double</code>).
     */
    public class Float64 extends Member {
        public Float64() {
            super(8, 8);
        }
        /**/
         public void set(double value) {
         getByteBuffer().putDouble(position(), value);
         }
         public double get() {
         return getByteBuffer().getDouble(position());
         }
         /**/
    }

    /**
     * <p> This class represents a 32 bits reference (C/C++ pointer) to 
     *     a {@link Struct} object (other types may require a {@link Struct}
     *     wrapper).</p>
     * <p> Note: For references which can be externally modified, an application
     *           may want to check the {@link #isUpToDate up-to-date} status of
     *           the reference. For out-of-date references, a new {@link Struct}
     *           can be created at the address specified by {@link #value} 
     *           (using JNI) and then {@link #set set} to the reference.</p>
     */
    public class Reference32 extends Member {
        private final Class _structClass;

        private Struct _struct;

        public Reference32(Class structClass) {
            super(4, 4);
            _structClass = structClass;
        }

        public void set(Struct struct) {
            if (_structClass.isInstance(struct)) {
                getByteBuffer().putInt(position(), (int) struct.address());
            } else if (struct == null) {
                getByteBuffer().putInt(position(), 0);
            } else {
                throw new IllegalArgumentException("struct: Is an instance of "
                        + struct.getClass() + ", instance of " + _structClass
                        + " expected");
            }
            _struct = struct;
        }

        public Struct get() {
            return _struct;
        }

        public int value() {
            return getByteBuffer().getInt(position());
        }

        public boolean isUpToDate() {
            if (_struct != null) {
                return getByteBuffer().getInt(position()) == (int) _struct
                        .address();
            } else {
                return getByteBuffer().getInt(position()) == 0;
            }
        }
    }

    /**
     * <p> This class represents a 64 bits reference (C/C++ pointer) to 
     *     a {@link Struct} object (other types may require a {@link Struct}
     *     wrapper).</p>
     * <p> Note: For references which can be externally modified, an application
     *           may want to check the {@link #isUpToDate up-to-date} status of
     *           the reference. For out-of-date references, a new {@link Struct}
     *           can be created at the address specified by {@link #value} 
     *           (using JNI) and then {@link #set set} to the reference.</p>
     */
    public class Reference64 extends Member {
        private final Class _structClass;

        private Struct _struct;

        public Reference64(Class structClass) {
            super(8, 8);
            _structClass = structClass;
        }

        public void set(Struct struct) {
            if (_structClass.isInstance(struct)) {
                getByteBuffer().putLong(position(), struct.address());
            } else if (struct == null) {
                getByteBuffer().putLong(position(), 0L);
            } else {
                throw new IllegalArgumentException("struct: Is an instance of "
                        + struct.getClass() + ", instance of " + _structClass
                        + " expected");
            }
            _struct = struct;
        }

        public Struct get() {
            return _struct;
        }

        public long value() {
            return getByteBuffer().getLong(position());
        }

        public boolean isUpToDate() {
            if (_struct != null) {
                return getByteBuffer().getLong(position()) == _struct.address();
            } else {
                return getByteBuffer().getLong(position()) == 0L;
            }
        }
    }

    /**
     * This class represents a 8 bits {@link Enum}.
     */
    public class Enum8 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        private final List _enumValues;

        public Enum8(List enumValues) {
            this(enumValues, 8);
        }

        public Enum8(List enumValues, int nbrOfBits) {
            super(1, nbrOfBits, 8);
            _enumValues = enumValues;
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 8 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public Enum get() {
            if (_mask == 0xFF) { // Non bit-field.
                return (Enum) _enumValues.get(getByteBuffer().get(position()));
            } else { // Bit-field.
                int value = getByteBuffer().get(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (Enum) _enumValues.get(value);
            }
        }

        public void set(Enum e) {
            int index = e.ordinal();
            if (_enumValues.get(index) != e) {
                throw new IllegalArgumentException(
                        "enum: "
                                + e
                                + ", ordinal value does not reflect enum values position");
            }
            byte value = (byte) index;
            if (_mask == 0xFF) { // Non bit-field.
                getByteBuffer().put(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().get(position()) & (~_mask);
                getByteBuffer().put(position(), (byte) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 16 bits {@link Enum}.
     */
    public class Enum16 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        private final List _enumValues;

        public Enum16(List enumValues) {
            this(enumValues, 16);
        }

        public Enum16(List enumValues, int nbrOfBits) {
            super(2, nbrOfBits, 16);
            _enumValues = enumValues;
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 16 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public Enum get() {
            if (_mask == 0xFFFF) { // Non bit-field.
                return (Enum) _enumValues.get(getByteBuffer().getShort(
                        position()));
            } else { // Bit-field.
                int value = getByteBuffer().getShort(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (Enum) _enumValues.get(value);
            }
        }

        public void set(Enum e) {
            int index = e.ordinal();
            if (_enumValues.get(index) != e) {
                throw new IllegalArgumentException(
                        "enum: "
                                + e
                                + ", ordinal value does not reflect enum values position");
            }
            short value = (short) index;
            if (_mask == 0xFFFF) { // Non bit-field.
                getByteBuffer().putShort(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getShort(position()) & (~_mask);
                getByteBuffer().putShort(position(), (short) (orMask | value));
            }
        }
    }

    /**
     * This class represents a 32 bits {@link Enum}.
     */
    public class Enum32 extends Member {
        private final int _mask;

        private final int _shift;

        private final int _signShift;

        private final List _enumValues;

        public Enum32(List enumValues) {
            this(enumValues, 32);
        }

        public Enum32(List enumValues, int nbrOfBits) {
            super(4, nbrOfBits, 32);
            _enumValues = enumValues;
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 32 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = (nbrOfBits == 32) ? 0xFFFFFFFF
                    : ((1 << nbrOfBits) - 1) << _shift;
            _signShift = 32 - _shift - nbrOfBits;
        }

        public Enum get() {
            if (_mask == 0xFFFFFFFF) { // Non bit-field.
                return (Enum) _enumValues.get(getByteBuffer()
                        .getInt(position()));
            } else { // Bit-field.
                int value = getByteBuffer().getInt(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (Enum) _enumValues.get(value);
            }
        }

        public void set(Enum e) {
            int index = e.ordinal();
            if (_enumValues.get(index) != e) {
                throw new IllegalArgumentException(
                        "enum: "
                                + e
                                + ", ordinal value does not reflect enum values position");
            }
            int value = index;
            if (_mask == 0xFFFFFFFF) { // Non bit-field.
                getByteBuffer().putInt(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                int orMask = getByteBuffer().getInt(position()) & (~_mask);
                getByteBuffer().putInt(position(), orMask | value);
            }
        }
    }

    /**
     * This class represents a 64 bits {@link Enum}.
     */
    public class Enum64 extends Member {
        private final long _mask;

        private final int _shift;

        private final int _signShift;

        private final List _enumValues;

        public Enum64(List enumValues) {
            this(enumValues, 64);
        }

        public Enum64(List enumValues, int nbrOfBits) {
            super(8, nbrOfBits, 64);
            _enumValues = enumValues;
            final int startBit = offset() << 3;
            _shift = (byteOrder() == ByteOrder.BIG_ENDIAN) ? 64 - _bitIndex
                    + startBit : _bitIndex - startBit - nbrOfBits;
            _mask = (nbrOfBits == 64) ? 0xFFFFFFFFFFFFFFFFl
                    : ((1l << nbrOfBits) - 1l) << _shift;
            _signShift = 64 - _shift - nbrOfBits;
        }

        public Enum get() {
            if (_mask == 0xFFFFFFFFFFFFFFFFl) { // Non bit-field.
                return (Enum) _enumValues.get((int) getByteBuffer().getLong(
                        position()));
            } else { // Bit-field.
                long value = getByteBuffer().getLong(position());
                value &= _mask;
                value <<= _signShift;
                value >>= _signShift + _shift; // Keeps sign.
                return (Enum) _enumValues.get((int) value);
            }
        }

        public void set(Enum e) {
            int index = e.ordinal();
            if (_enumValues.get(index) != e) {
                throw new IllegalArgumentException(
                        "enum: "
                                + e
                                + ", ordinal value does not reflect enum values position");
            }
            long value = index;
            if (_mask == 0xFFFFFFFFFFFFFFFFl) { // Non bit-field.
                getByteBuffer().putLong(position(), value);
            } else { // Bit-field.
                value <<= _shift;
                value &= _mask;
                long orMask = getByteBuffer().getLong(position()) & (~_mask);
                getByteBuffer().putLong(position(), orMask | value);
            }
        }
    }
}

