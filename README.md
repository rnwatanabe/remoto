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
</article>
  </div>

</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" action="" class="js-jump-to-line-form" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="btn">Go</button>
</form></div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer" role="contentinfo">
    <ul class="site-footer-links right">
        <li><a href="https://status.github.com/" data-ga-click="Footer, go to status, text:status">Status</a></li>
      <li><a href="https://developer.github.com" data-ga-click="Footer, go to api, text:api">API</a></li>
      <li><a href="https://training.github.com" data-ga-click="Footer, go to training, text:training">Training</a></li>
      <li><a href="https://shop.github.com" data-ga-click="Footer, go to shop, text:shop">Shop</a></li>
        <li><a href="https://github.com/blog" data-ga-click="Footer, go to blog, text:blog">Blog</a></li>
        <li><a href="https://github.com/about" data-ga-click="Footer, go to about, text:about">About</a></li>
      <li><a href="https://help.github.com" data-ga-click="Footer, go to help, text:help">Help</a></li>

    </ul>

    <a href="https://github.com" aria-label="Homepage">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
</a>
    <ul class="site-footer-links">
      <li>&copy; 2015 <span title="0.06719s from github-fe126-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="https://github.com/site/terms" data-ga-click="Footer, go to terms, text:terms">Terms</a></li>
        <li><a href="https://github.com/site/privacy" data-ga-click="Footer, go to privacy, text:privacy">Privacy</a></li>
        <li><a href="https://github.com/security" data-ga-click="Footer, go to security, text:security">Security</a></li>
        <li><a href="https://github.com/contact" data-ga-click="Footer, go to contact, text:contact">Contact</a></li>
    </ul>
  </div>
</div>


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-suggester-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="fullscreen-contents js-fullscreen-contents" placeholder=""></textarea>
      <div class="suggester-container">
        <div class="suggester fullscreen-suggester js-suggester js-navigation-container"></div>
      </div>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    
    

    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-x flash-close js-ajax-error-dismiss" aria-label="Dismiss error"></a>
      Something went wrong with that request. Please try again.
    </div>


      <script crossorigin="anonymous" src="https://assets-cdn.github.com/assets/frameworks-808fcfcd63c9ecba3e84453f540cb1cbafde48c6b30c1d51ebd4e67e88ff66bd.js"></script>
      <script async="async" crossorigin="anonymous" src="https://assets-cdn.github.com/assets/github/index-4f888c77edb689a0c50e2edbc2d7ae11518355eff0bfeac3ab0a110ca10eddf9.js"></script>
      
      
  </body>
</html>

=======
# remoto

ReMoto is a web-based neuronal simulation system, intended for studying spinal cord neuronal networks responsible for muscle control. These networks are affected by descending drive, afferent drive, and electrical nerve stimulation. The simulator may be used to investigate phenomena at several levels of organization, e.g., at the neuronal membrane level or at the whole muscle behavior level (e.g., muscle force generation). This versatility is due to the fact that each element (neurons, synapses, muscle fibers) has its own specific mathematical model, usually involving the action of voltage- or neurotransmitter-dependent ionic channels. The simulator should be helpful in activities such as interpretation of results obtained from neurophysiological experiments in humans, proposal of hypothesis or testing models or theories on neuronal dynamics or neuronal network processing, validation of experimental protocols, and teaching neurophysiology.

The elements that take part in the system belong to the following classes: motoneurons, muscle fibers (electrical activity and force generation), Renshaw cells, Ia inhibitory interneurons, Ib inhibitory interneurons, Ia and Ib afferents. The neurons are interconnected by chemical synapses, which can be exhibit depression or facilitation.

The system simulates the following nuclei involved in flexion and extension of the human ankle: Medial Gastrocnemius (MG), Lateral Gastrocnemius (LG), Soleus (SOL), and Tibialis Anterior (TA).

Steps to install:

1 - Downloand Java SE, Java JDK, Tomcat 7.0 and Eclipse.

2 - Install Java SE.

3 - Install Java JDK.

4 - Copy all the files into a folder named "remoto". The address of this folder will be called in this text as "remotoPath"

5 - Open the Eclipse and point the workspace to the folder "remotoPath". 

6 - Click "Help" -> "Install New Software".

7 - Install the following:
	
	* Eclipse Java EE Developer Tools
	
	* Eclipse Java Web Developer Tools
	
	* Eclipse Web Developer Tools
	
	* JST Server Adapters
	
	* JST Server adapters Extensions
	
	* JST UI

8 - Restart Eclipse;

9 - Click "Window" -> "Preferences".

10 - Inside "Preferences" double-click "Server" -> "Runtime Environments".

11 - If the Tomcat runtime environment is present, remove it.

12 - Create a new "Apache Tomcat 7.0" runtime environment and configure the Installation directory to the folder you installed Tomcat 7.0.

13 - In the "Project Explorer" tab in the Worspace, click in the "remoto" project.

14 - Click "Project" -> "Properties".

15 - Inside "Properties" click "Java Build Path". Select the tab "Libraries".

16 - If the "JRE System Library" is present, remove it.

17 - Click "Add Library" and select "JRE System Library". Click "Next" and then "Finish".

18 - In the "Servers" tab delete any existent Server.

19 - Left-click inside the "Servers" tab and click "New" -> "Server"

20 - Select "Tomcat v7.0 Server" and click "Next".

21 - In the "Available" box, click in "remoto"  and then click "Add" and "Finish".

22 - Double click in "Tomcat v7.0 Server at localhost" at the "Server" tab.

23 - A new Tab will open. Click in "Ports".

24 - Change the Port Number of the "HTTP/1.1" Port to 80.

25 - At the "Project Explorer" tab in the Workspace click, "Java Resources"->"src"->"br.remoto"->"dao".

26 - Double-click at the "BasicDAO.java" file.

27 - At the line 46, substitute "143.107.162.166" by the IP address of your computer. Save the file. 

28 - At the "remotoPath" Folder, open the "WebContent/db" folder. Open the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows) in any text editor.

29 - In this file, subsititute the address of the hsqldb.jar file to "remotoPath/WebContent/WEB-INF/lib/hsqldb.jar" and the address of the remoto database to "remotoPath/WebContent/db/remoto". Save the file.

If you do not have a Static IP address subsititute the steps 24-27 by the following:

	24.1 - Maintain the Port Number of the "HTTP/1.1" Port as 8080.

	25.1 - At the "Project Explorer" tab in the Workspace click, "Java Resources"->"src"->"br.remoto"->"dao".

	26.1 - Double-click at the "BasicDAO.java" file.

	27.1 - At the line 46, substitute "143.107.162.166" by "localhost". Save the file. 


Steps to execute:

1 - Execute the file "start.sh" (if you are using Linux) or "start.bat" (if you are using Windows).

2 - Open the Eclipse  and point the workspace to the folder "remotoPath". 

3 - In the "Servers" tab, left-click in "Tomcat v7.0 Server at localhost". Click "Start".

4 - In a web-browser, type "youripaddress/remoto/overview.htm". "youripaddress" is the IP address of your computer. 

5 - If you do not have a Static IP address and followed step 27.1 during the installation process, type in a web-browser "localhost:8080/remoto/overview.htm".

5 - After any change in the code, you have to save the files and Restart the server by left-clicking in "Tomcat v7.0 Server at localhost" and then click "Restart".
>>>>>>> cdc867139da838d9145c2683bac8b645cd52cda7
