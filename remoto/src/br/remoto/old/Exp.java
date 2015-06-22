package br.remoto.old;


public class Exp extends Union
{
	private Signed32   asInt    = new Signed32();
	private Float64    asFloat  = new Float64();

	private static final int a = 1512775;
	private static final int b = 1072693248;
	private static final int c = 61;
	private static final int d = b - c;
	
	
	public static void main(String[] args) 
	{
		// Teste
		Exp exp = new Exp();
		
		double y = -.2;
		double x = exp.calc(y);
		
		System.out.println( x );
		System.out.println( Math.exp( y ) );
	}
	
	
	public double calc(double y)
	{
		asInt.set((int)( a * y + d ) );
		
		return asFloat.get();
	}
	
}
