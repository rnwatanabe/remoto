# ReMoto

This repository has the Java code to a web-based neuronal simulation system. It has been developed over the last ten years by some authors, including me. The version in this repository is the code as I am using it now. An on-line version of this simulator can be found in: http://remoto.leb.usp.br. 

ReMoto is a web-based neuronal simulation system, intended for studying spinal cord neuronal networks responsible for muscle control. These networks are affected by descending drive, afferent drive, and electrical nerve stimulation. The simulator may be used to investigate phenomena at several levels of organization, e.g., at the neuronal membrane level or at the whole muscle behavior level (e.g., muscle force generation). This versatility is due to the fact that each element (neurons, synapses, muscle fibers) has its own specific mathematical model, usually involving the action of voltage- or neurotransmitter-dependent ionic channels. The simulator should be helpful in activities such as interpretation of results obtained from neurophysiological experiments in humans, proposal of hypothesis or testing models or theories on neuronal dynamics or neuronal network processing, validation of experimental protocols, and teaching neurophysiology.

The elements that take part in the system belong to the following classes: motoneurons, muscle fibers (electrical activity and force generation), Renshaw cells, Ia inhibitory interneurons, Ib inhibitory interneurons, Ia and Ib afferents. The neurons are interconnected by chemical synapses, which can be exhibit depression or facilitation.

The system simulates the following nuclei involved in flexion and extension of the human ankle: Medial Gastrocnemius (MG), Lateral Gastrocnemius (LG), Soleus (SOL), and Tibialis Anterior (TA).


## Installation Guide

This guide will walk you through the installation process for ReMoto. While the web-based architecture can make installation challenging, these step-by-step instructions should help you get started. The system has been tested on both Windows and Ubuntu platforms.

### Prerequisites

> [!TIP]  
> It is strongly recommended to use the exact versions of Apache Tomcat 7.0 and Eclipse IDE 2025-03 provided in the links below to ensure compatibility with ReMoto. Using different versions may lead to installation issues.

Before beginning, ensure you have the following software installed:

- [Apache Tomcat 7.0](https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.109/bin/)
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/release/2025-03/r)

### Step 1: Install Eclipse Plugins

1. Launch Eclipse IDE
2. Navigate to `Help` → `Install New Software...`
3. In the "Work with" field, enter: `https://download.eclipse.org/releases/2025-03`
4. Expand the section `Web, XML, Java EE and OSGi Enterprise Developement`.
5. Select and install the following components:
   - Eclipse Java EE Developer Tools
   - Eclipse Java Web Developer Tools
   - Eclipse Web Developer Tools
   - JST Server Adapters
   - JST Server Adapters Extensions
   - JST UI
6. Accept the license agreements and restart Eclipse when prompted

### Step 2: Clone and Import the Repository

1. Clone the repository:
   ```bash
   git clone https://github.com/RaulSimpetru/remoto
   ```
> [!NOTE]  
> The path to the cloned repository will be referred to as `remotoPath` throughout this guide.
2. In Eclipse:
   - Go to `File` → `Open Projects from File System...`
   - Select the cloned repository folder

### Step 3: Configure Apache Tomcat

1. In Eclipse, navigate to `Window` → `Preferences`
2. Expand `Server` → `Runtime Environments`
3. Remove any existing Tomcat runtime environment
4. Click `Add` and select `Apache` → `Apache Tomcat 7.0`
5. Set the installation directory to your Tomcat 7.0 location. By default:
   - Windows: `C:\Program Files\Apache Software Foundation\Tomcat 7.0`  
   - Linux: `/usr/share/tomcat7`

### Step 4: Project Configuration

1. In Project Explorer, select the `remoto` project
2. Go to `Project` → `Properties`
3. Configure Java Build Path:
   - Select `Java Build Path` → `Libraries` tab
   - Remove existing `JRE System Library` if present
   - Click `Add Library` → `JRE System Library` → `Next` → `Finish`
4. Configure Java Compiler:
   - Select `Java Compiler`
   - Enable `Use default compliance settings`
5. Click `Apply` to save changes

### Step 5: Update Java Project Facet

1. In the Eclipse main window, open the `Problems` view
2. Locate the error: "Java compiler level does not match the version of the installed Java project facet"
3. Right-click the error and select `Quick Fix`
4. Choose `Change Java project facet version to Java 1.8`
5. Click `Finish`

> [!NOTE]  
> You may see two errors with the following message: "Access restriction: The type 'Type' is not AP" ...  
> These are expected and can be safely ignored.

### Step 6: Server Configuration

1. Open the `Servers` view:
   - Go to `Window` → `Show View` → `Other...` → `Servers`
2. Remove any existing servers
3. Create a new server:
   - Right-click in the `Servers` view
   - Select `New` → `Server`
   - Choose `Tomcat v7.0 Server`
   - Add the `remoto` project to the server
4. Configure server ports:
   - Double-click `Tomcat v7.0 Server at localhost`
   - Go to the `Ports` tab
   - Set `HTTP/1.1` port to `80` (or `8080` for non-static IP)
   - Set `Tomcat admin port` to an available port (e.g., `1`)
5. Save the configuration (Ctrl + S)

### Step 7: Database Setup

1. Configure the database connection:
   - Navigate to `src` → `br.remoto.dao`
   - Open `BasicDAO.java`
   - Update line 46 with your computer's IP address (use `"localhost"` for non-static IP)
2. Configure database startup:
   - Open `WebContent/db/start.sh` (Linux) or `start.bat` (Windows)
   - Update the path to `hsqldb.jar`: `remotoPath/WebContent/WEB-INF/lib/hsqldb.jar`
   - Update the database path: `remotoPath/WebContent/db/remoto`

### Step 8: Launch the Application

1. Start the database:
   - Run `start.sh` (Linux) or `start.bat` (Windows)
2. In Eclipse:
   - Start the server from the `Servers` view
3. Access the application:
   - For static IP: `http://youripaddress/remoto/overview.htm`
   - For localhost: `http://localhost:8080/remoto/overview.htm`
4. After making code changes:
   - Save all files
   - Restart the server from the `Servers` view

For additional support or to report issues, please open an issue on the repository or contact the maintainer.

## How to cite this work

If you use this code, please cite the following papers:

"CISI, R. R. L.; KOHN, A. F. Simulation system of spinal cord motor nuclei and associated
nerves and muscles, in a Web-based architecture. Journal of Computational Neuroscience,
v. 25, n. 3, p. 520–542, 2008"

"ELIAS, L. A.; CHAUD, V. M.; KOHN, A. F. Models of passive and active dendrite motoneuron
pools and their differences in muscle force control. Journal of Computational Neuroscience,
Springer Netherlands, v. 33, n. 3, p. 515–531, 2012."

"ELIAS, L. A.; KOHN, A. F. Individual and collective properties of computationally efficient
motoneuron models of types S and F with active dendrites. Neurocomputing, v. 99, p.
521–533, 2013."

"WATANABE, R. N. et al. Influences of premotoneuronal command statistics on the scaling of
motor output variability during isometric plantarflexion. Journal of Neurophysiology, v. 110,
n. 11, p. 2592–2606, set. 2013."

"ELIAS, L. A.; WATANABE, R. N.; KOHN, A. F. Spinal mechanisms may provide a
combination of intermittent and continuous control of human posture: predictions from a
biologically based neuromusculoskeletal model. PLoS Computational Biology, v. 10, n. 11,
p. e1003944, nov. 2014."

"WATANABE, R. N.; KOHN, A. F. Fast Oscillatory Commands from the Motor Cortex Can
Be Decoded by the Spinal Cord for Force Control. Journal of Neuroscience, v. 35, n. 40, p.
13687–13697, 2015."

The Bibtex file of these references can be found [here](https://github.com/rnwatanabe/remoto/blob/master/ref.bib).

### BibTeX Citation

You can cite the relevant papers using the following BibTeX entries. Copy and paste these into your `.bib` file:

```bibtex
@article{Cisi2008,
  author = {Cisi, R R L and Kohn, A F},
  title = {Simulation system of spinal cord motor nuclei and associated nerves and muscles, in a Web-based architecture},
  journal = {Journal of Computational Neuroscience},
  volume = {25},
  number = {3},
  pages = {520--542},
  year = {2008}
}

@article{Elias2012,
  author = {Elias, Leonardo Abdala and Chaud, Vitor Martins and Kohn, Andr{\'{e}} F{\'{a}}bio},
  title = {Models of passive and active dendrite motoneuron pools and their differences in muscle force control},
  journal = {Journal of Computational Neuroscience},
  volume = {33},
  number = {3},
  pages = {515--531},
  year = {2012}
}

@article{Elias2013,
  author = {Elias, Leonardo Abdala and Kohn, Andr{\'{e}} Fabio},
  title = {Individual and collective properties of computationally efficient motoneuron models of types S and F with active dendrites},
  journal = {Neurocomputing},
  volume = {99},
  pages = {521--533},
  year = {2013}
}

@article{Watanabe2013,
  author = {Watanabe, Renato Naville and Magalh{\~{a}}es, Fernando Henrique and Elias, Leonardo Abdala and Chaud, Vitor Martins and Mello, Emanuele Moraes and Kohn, Andre F},
  title = {Influences of premotoneuronal command statistics on the scaling of motor output variability during isometric plantarflexion},
  journal = {Journal of Neurophysiology},
  volume = {110},
  number = {11},
  pages = {2592--2606},
  year = {2013}
}

@article{Elias2014,
  author = {Elias, Leonardo Abdala and Watanabe, Renato Naville and Kohn, Andr{\'{e}} Fabio},
  title = {Spinal mechanisms may provide a combination of intermittent and continuous control of human posture: predictions from a biologically based neuromusculoskeletal model},
  journal = {PLoS Computational Biology},
  volume = {10},
  number = {11},
  pages = {e1003944},
  year = {2014}
}

@article{Watanabe2015,
  author = {Watanabe, R N and Kohn, A F},
  title = {Fast Oscillatory Commands from the Motor Cortex Can Be Decoded by the Spinal Cord for Force Control},
  journal = {Journal of Neuroscience},
  volume = {35},
  number = {40},
  pages = {13687--13697},
  year = {2015}
}
```
