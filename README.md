#ReMoto


<p>ReMoto is a web-based neuronal simulation system, intended for studying spinal cord neuronal networks responsible for muscle control. These networks are affected by descending drive, afferent drive, and electrical nerve stimulation. The simulator may be used to investigate phenomena at several levels of organization, e.g., at the neuronal membrane level or at the whole muscle behavior level (e.g., muscle force generation). This versatility is due to the fact that each element (neurons, synapses, muscle fibers) has its own specific mathematical model, usually involving the action of voltage- or neurotransmitter-dependent ionic channels. The simulator should be helpful in activities such as interpretation of results obtained from neurophysiological experiments in humans, proposal of hypothesis or testing models or theories on neuronal dynamics or neuronal network processing, validation of experimental protocols, and teaching neurophysiology.</p>

<p>The elements that take part in the system belong to the following classes: motoneurons, muscle fibers (electrical activity and force generation), Renshaw cells, Ia inhibitory interneurons, Ib inhibitory interneurons, Ia and Ib afferents. The neurons are interconnected by chemical synapses, which can be exhibit depression or facilitation.</p>

<p>The system simulates the following nuclei involved in flexion and extension of the human ankle: Medial Gastrocnemius (MG), Lateral Gastrocnemius (LG), Soleus (SOL), and Tibialis Anterior (TA).</p>

<p>Steps to install:</p>

<p>1 - Downloand Java SE JDK, Tomcat 7.0 and Eclipse.</p>

<p>2 - Install Java JDK.</p>

<p>3 - Copy all the files into a folder named "remoto". The address of this folder will be called in this text as "remotoPath"</p>

<p>4 - Open the Eclipse and point the workspace to the folder "remotoPath". </p>

<p>5 - Click "Help" -&gt; "Install New Software".</p>

<p>6 - Install the following:</p>

<pre><code>* Eclipse Java EE Developer Tools

* Eclipse Java Web Developer Tools

* Eclipse Web Developer Tools

* JST Server Adapters

* JST Server adapters Extensions

* JST UI
</code></pre>

<p>7 - Restart Eclipse;</p>

<p>8 - Click "Window" -&gt; "Preferences".</p>

<p>9 - Inside "Preferences" double-click "Server" -&gt; "Runtime Environments".</p>

<p>10 - If the Tomcat runtime environment is present, remove it.</p>

<p>11 - Create a new "Apache Tomcat 7.0" runtime environment and configure the Installation directory to the folder you installed Tomcat 7.0.</p>

<p>12 - In the "Project Explorer" tab in the Worspace, click in the "remoto" project.</p>

<p>13 - Click "Project" -&gt; "Properties".</p>

<p>14 - Inside "Properties" click "Java Build Path". Select the tab "Libraries".</p>

<p>15 - If the "JRE System Library" is present, remove it.</p>

<p>16 - Click "Add Library" and select "JRE System Library". Click "Next" and then "Finish".</p>

<p>17 - In the "Servers" tab delete any existent Server.</p>

<p>18 - Left-click inside the "Servers" tab and click "New" -&gt; "Server"</p>

<p>19 - Select "Tomcat v7.0 Server" and click "Next".</p>

<p>20 - In the "Available" box, click in "remoto"  and then click "Add" and "Finish".</p>

<p>21 - Double click in "Tomcat v7.0 Server at localhost" at the "Server" tab.</p>

<p>22 - A new Tab will open. Click in "Ports".</p>

<p>23 - Change the Port Number of the "HTTP/1.1" Port to 80.</p>

<p>24 - At the "Project Explorer" tab in the Workspace click, "Java Resources"-&gt;"src"-&gt;"br.remoto"-&gt;"dao".</p>

<p>25 - Double-click at the "BasicDAO.java" file.</p>

<p>26 - At the line 46, substitute "143.107.162.166" by the IP address of your computer. Save the file. </p>

<p>27 - At the "remotoPath" Folder, open the "WebContent/db" folder. Open the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows) in any text editor.</p>

<p>28 - In this file, subsititute the address of the hsqldb.jar file to "remotoPath/WebContent/WEB-INF/lib/hsqldb.jar" and the address of the remoto database to "remotoPath/WebContent/db/remoto". Save the file.</p>

<p>If you do not have a Static IP address subsititute the steps 23-26 by the following:</p>

<pre><code>23.1 - Maintain the Port Number of the "HTTP/1.1" Port as 8080.

24.1 - At the "Project Explorer" tab in the Workspace click, "Java Resources"-&gt;"src"-&gt;"br.remoto"-&gt;"dao".

25.1 - Double-click at the "BasicDAO.java" file.

26.1 - At the line 46, substitute "143.107.162.166" by "localhost". Save the file. 
</code></pre>

<p>Steps to execute:</p>

<p>1 - Execute the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows).</p>

<p>2 - Open the Eclipse  and point the workspace to the folder "remotoPath". </p>

<p>3 - In the "Servers" tab, left-click in "Tomcat v7.0 Server at localhost". Click "Start".</p>

<p>4 - In a web-browser, type "youripaddress/remoto/overview.htm". "youripaddress" is the IP address of your computer. </p>

<p>5 - If you do not have a Static IP address and followed step 27.1 during the installation process, type in a web-browser "localhost:8080/remoto/overview.htm".</p>

<p>5 - After any change in the code, you have to save the files and Restart the server by left-clicking in "Tomcat v7.0 Server at localhost" and then click "Restart".</p>

