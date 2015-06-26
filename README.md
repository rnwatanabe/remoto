#ReMoto


<p>ReMoto is a web-based neuronal simulation system, intended for studying spinal cord neuronal networks responsible for muscle control. These networks are affected by descending drive, afferent drive, and electrical nerve stimulation. The simulator may be used to investigate phenomena at several levels of organization, e.g., at the neuronal membrane level or at the whole muscle behavior level (e.g., muscle force generation). This versatility is due to the fact that each element (neurons, synapses, muscle fibers) has its own specific mathematical model, usually involving the action of voltage- or neurotransmitter-dependent ionic channels. The simulator should be helpful in activities such as interpretation of results obtained from neurophysiological experiments in humans, proposal of hypothesis or testing models or theories on neuronal dynamics or neuronal network processing, validation of experimental protocols, and teaching neurophysiology.</p>

<p>The elements that take part in the system belong to the following classes: motoneurons, muscle fibers (electrical activity and force generation), Renshaw cells, Ia inhibitory interneurons, Ib inhibitory interneurons, Ia and Ib afferents. The neurons are interconnected by chemical synapses, which can be exhibit depression or facilitation.</p>

<p>The system simulates the following nuclei involved in flexion and extension of the human ankle: Medial Gastrocnemius (MG), Lateral Gastrocnemius (LG), Soleus (SOL), and Tibialis Anterior (TA).</p>

<p>Steps to install:</p>

<p>1 - Downloand Java SE, Java JDK, Tomcat 7.0 and Eclipse.</p>

<p>2 - Install Java SE.</p>

<p>3 - Install Java JDK.</p>

<p>4 - Copy all the files into a folder named "remoto". The address of this folder will be called in this text as "remotoPath"</p>

<p>5 - Open the Eclipse and point the workspace to the folder "remotoPath". </p>

<p>6 - Click "Help" -&gt; "Install New Software".</p>

<p>7 - Install the following:</p>

<pre><code>* Eclipse Java EE Developer Tools

* Eclipse Java Web Developer Tools

* Eclipse Web Developer Tools

* JST Server Adapters

* JST Server adapters Extensions

* JST UI
</code></pre>

<p>8 - Restart Eclipse;</p>

<p>9 - Click "Window" -&gt; "Preferences".</p>

<p>10 - Inside "Preferences" double-click "Server" -&gt; "Runtime Environments".</p>

<p>11 - If the Tomcat runtime environment is present, remove it.</p>

<p>12 - Create a new "Apache Tomcat 7.0" runtime environment and configure the Installation directory to the folder you installed Tomcat 7.0.</p>

<p>13 - In the "Project Explorer" tab in the Worspace, click in the "remoto" project.</p>

<p>14 - Click "Project" -&gt; "Properties".</p>

<p>15 - Inside "Properties" click "Java Build Path". Select the tab "Libraries".</p>

<p>16 - If the "JRE System Library" is present, remove it.</p>

<p>17 - Click "Add Library" and select "JRE System Library". Click "Next" and then "Finish".</p>

<p>18 - In the "Servers" tab delete any existent Server.</p>

<p>19 - Left-click inside the "Servers" tab and click "New" -&gt; "Server"</p>

<p>20 - Select "Tomcat v7.0 Server" and click "Next".</p>

<p>21 - In the "Available" box, click in "remoto"  and then click "Add" and "Finish".</p>

<p>22 - Double click in "Tomcat v7.0 Server at localhost" at the "Server" tab.</p>

<p>23 - A new Tab will open. Click in "Ports".</p>

<p>24 - Change the Port Number of the "HTTP/1.1" Port to 80.</p>

<p>25 - At the "Project Explorer" tab in the Workspace click, "Java Resources"-&gt;"src"-&gt;"br.remoto"-&gt;"dao".</p>

<p>26 - Double-click at the "BasicDAO.java" file.</p>

<p>27 - At the line 46, substitute "143.107.162.166" by the IP address of your computer. Save the file. </p>

<p>28 - At the "remotoPath" Folder, open the "WebContent/db" folder. Open the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows) in any text editor.</p>

<p>29 - In this file, subsititute the address of the hsqldb.jar file to "remotoPath/WebContent/WEB-INF/lib/hsqldb.jar" and the address of the remoto database to "remotoPath/WebContent/db/remoto". Save the file.</p>

<p>If you do not have a Static IP address subsititute the steps 24-27 by the following:</p>

<pre><code>24.1 - Maintain the Port Number of the "HTTP/1.1" Port as 8080.

25.1 - At the "Project Explorer" tab in the Workspace click, "Java Resources"-&gt;"src"-&gt;"br.remoto"-&gt;"dao".

26.1 - Double-click at the "BasicDAO.java" file.

27.1 - At the line 46, substitute "143.107.162.166" by "localhost". Save the file. 
</code></pre>

<p>Steps to execute:</p>

<p>1 - Execute the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows).</p>

<p>2 - Open the Eclipse  and point the workspace to the folder "remotoPath". </p>

<p>3 - In the "Servers" tab, left-click in "Tomcat v7.0 Server at localhost". Click "Start".</p>

<p>4 - In a web-browser, type "youripaddress/remoto/overview.htm". "youripaddress" is the IP address of your computer. </p>

<p>5 - If you do not have a Static IP address and followed step 27.1 during the installation process, type in a web-browser "localhost:8080/remoto/overview.htm".</p>

<p>5 - After any change in the code, you have to save the files and Restart the server by left-clicking in "Tomcat v7.0 Server at localhost" and then click "Restart".</p>

