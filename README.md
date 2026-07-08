LiberM Refactor Environment Setup & Execution Guide.

```markdown
# Preface

This comprehensive guide walks you through the step-by-step installation, configuration, and runtime execution of the **LiberM Refactor Environment** using the **AWS Card Demo** sample project. 
```

```Table of Contents
# 📋 Table of Contents

1. [Prerequisites](#1-prerequisites)
2. [Environment Variables Setup](#2-environment-variables-setup)
3. [Maven & GitHub Authentication Configuration](#3-maven--github-authentication-configuration)
4. [Project Packages & Directory Layout](#4-project-packages--directory-layout)
5. [The LiberM Refactor Process (Code Conversion)](#5-the-liberm-refactor-process-code-conversion)
6. [Building the Converted Project](#6-building-the-converted-project)
7. [Dockerized Runtime Infrastructure](#7-dockerized-runtime-infrastructure)
8. [Running the Application (Online Transactions)](#8-running-the-application-online-transactions)
9. [Running Batch Jobs (Liber Batch / Motorhead)](#9-running-batch-jobs-liber-batch--motorhead)
10. [Troubleshooting & Log Analysis](#10-troubleshooting--log-analysis)

```
## 1. Prerequisites

Ensure your workstation has the following mandatory software and toolchains installed before proceeding:

| Sl. No. | Component | Specification / Recommended Version |
| :--- | :--- | :--- |
| 1 | **JDK** | Java 21 |
| 2 | **Build Tool** | Apache Maven |
| 3 | **Node.js** | Version 21 (Tested on `v21.7.3`) |
| 4 | **Container Engine** | Docker Desktop |
| 5 | **IDE** | IntelliJ IDEA |
| 6 | **Database Client** | DBeaver |
| 7 | **Terminal Emulator** | TN3270 Emulator (e.g., TN3270 Plus) |
| 8 | **Version Control** | GitHub Desktop or Git Bash |
| 9 | **API Client (Optional)**| Postman |

```TODO -> install checking
javac -version
javac 24.0.1

mvn -version        # winget install Apache.Maven     OR    # powershell: choco install maven
Apache Maven 3.9.9 

git --version       # winget install Git.Git
git version 2.45.2.windows.1

#NodeJS
node -v             # winget install OpenJS.NodeJS
v24.13.1

npm -v
11.8.0

# docker check
docker --version        # winget install Docker.DockerDesktop
Docker version 27.3.1, build ce12230

docker compose version
Docker Compose version v2.30.3-desktop.1

# Verify IntelliJ Installed
winget install dbeaver.dbeaver    # powershell

# Install DBeaver
winget install dbeaver.dbeaver

# check JAVA Home env var
echo %JAVA_HOME%
echo %PATH%

```

## 2. Environment Variables Setup

Before invoking build scripts or operating your IDE, you must register the required system or user environment variables. 

On Windows, execute the following commands in an Administrative PowerShell session or configure them via System Properties:



```TODO -> powershell
# Set the valid enterprise operational license key for LiberM
[Environment]::SetEnvironmentVariable("NIB_JAVA_LICENSE_KEY", "rxg1bu9TBifBg9UpiL1CHNBwMrkQUkiCntvR7jzu91O/jFLtueTcaUV41rUNhY0V/GDD0PKvmrdqc3t346bRLETlH1GRE/v9N2jSSpEFafhwHRfCQHhwnGoSuHl9SAzxTN+JFyzEMTy4MVF2cD/ZH5nenPSlDc5eDn59cz9Y6PI/6m2Y1SICXEKfBXU7IbX0xs2Hm175wdS2HE5VcLxz8ut4wNBu2VY120Ru2BFn4iPvAqbsyaQ6dnXbBx0CNK0KaDYmow0u4Wl94+Ga2hzjOWtHo3msVo+rjzswKJQJ39SIUeNXZPWs1gL4rQ/Fcw99X0rOrP1dI5g9CRuQL+ybeqV3Tlq47pNMKdTvOp6z+1rKAsffdYcX8VBmY/sdVdcR9gXQvGgeuwNXiwuapFDwj/Rn+PUgsFNSo1WyR7nl3kSBQ1Oqd/flUh7pOMBZ3XvL", "User")
$env:NIB_JAVA_LICENSE_KEY

# Set the home variable to the target path of your root project
[Environment]::SetEnvironmentVariable("NIB_JAVA_DEMO_HOME", "D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps", "User")
$env:NIB_JAVA_DEMO_HOME

```

```
#                    OR
```

```windows command-prompt
setx NIB_JAVA_LICENSE_KEY "rxg1bu9TBifBg9UpiL1CHNBwMrkQUkiCntvR7jzu91O/jFLtueTcaUV41rUNhY0V/GDD0PKvmrdqc3t346bRLETlH1GRE/v9N2jSSpEFafhwHRfCQHhwnGoSuHl9SAzxTN+JFyzEMTy4MVF2cD/ZH5nenPSlDc5eDn59cz9Y6PI/6m2Y1SICXEKfBXU7IbX0xs2Hm175wdS2HE5VcLxz8ut4wNBu2VY120Ru2BFn4iPvAqbsyaQ6dnXbBx0CNK0KaDYmow0u4Wl94+Ga2hzjOWtHo3msVo+rjzswKJQJ39SIUeNXZPWs1gL4rQ/Fcw99X0rOrP1dI5g9CRuQL+ybeqV3Tlq47pNMKdTvOp6z+1rKAsffdYcX8VBmY/sdVdcR9gXQvGgeuwNXiwuapFDwj/Rn+PUgsFNSo1WyR7nl3kSBQ1Oqd/flUh7pOMBZ3XvL"

setx NIB_JAVA_DEMO_HOME "D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps"   
```

----------

*Note: Restart your terminal or IDE after defining these variables to ensure they are inherited by active processes.*

---

## 3. Maven & GitHub Authentication Configuration

The foundational `mLogica` framework artifacts are securely hosted on a private GitHub Packages repository. GitHub requires an authenticated user token to fetch dependency packages, even if the target repository is publicly visible.

### Step 3.1: Generate a GitHub Fine-Grained Personal Access Token (PAT)

1. Sign in to your [GitHub Account](https://github.com).
2. Click your profile picture in the top-right corner $\rightarrow$ select **Settings**.
3. Scroll down the left-hand sidebar and click **Developer settings**.
4. Expand **Personal access tokens** $\rightarrow$ click **Fine-grained tokens**.
5. Click **Generate new token**. Define a descriptive name, set an expiration window, and assign read-only access to packages.
6. Copy the generated token string immediately (`github_pat_...`).

```TODO -> GIT Repo - Create a new repository
    Login GIT Portal 
    Goto Home
    Click NEW Repo Button to Create a New Repo
    Make it PRIVATE 
```

```TODO -> GIT Repo - PUSH Code to Repo using command line

echo "# LiberM_Refactor_Angular" >> README.md
git init
git add .
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/inayat68/LiberM_Refactor_Angular.git
git push -u origin main

```

```TODO -> GIT Repo Push an existing repository from the command line

git remote add origin https://github.com/inayat68/LiberM_Refactor_Angular.git
git branch -M main
git push -u origin main

```

### Step 3.2: Configure `settings.xml`

```TODO -> open directory

#open directory path
C:\Users\<username>\.m2

#create file inside
settings.xml

#open file in notepad
notepad %USERPROFILE%\.m2\settings.xml
```


Navigate to your local machine’s Maven home profile folder (typically `C:\Users\<your-user>\.m2\`) and add your credentials inside the `<servers>` block of your `settings.xml` file:

```xml

<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
<servers>
    <server>
        <id>github</id>
        <username>YOUR_GITHUB_USERNAME</username>
        <password>YOUR_GITHUB_PERSONAL_ACCESS_TOKEN</password>
    </server>
</servers>
 
</settings>

```

### Step 3.3: Configure Project `pom.xml`

The framework lookup endpoint must be declared inside the project's foundational `pom.xml` configuration file:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>[https://maven.pkg.github.com/nib-labs/nib-java](https://maven.pkg.github.com/nib-labs/nib-java)</url>
    </repository>
</repositories>

```

---

## 4. Project Packages & Directory Layout

Download the structural source archives and emulator assets from the designated **mLogica External Projects Management Team** SharePoint directory:

* `aws_cards_demo.zip` (Mainframe architecture artifacts & migration configuration engines)
* `nib-java-demo-apps.zip` (Pre-factored operational code layouts and runtime blocks)
* `TN3270Plus.zip` (Terminal user interface software asset)

``` TODO -> download 3 ZIP Project Files using following URL:

https://mlogicainc.sharepoint.com/sites/MlogicaExternalProjectsManagementTeam/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2FMlogicaExternalProjectsManagementTeam%2FShared%20Documents%2FGeneral%2FAll%20Projects%2FLIBERm%20Roadmap%2FTransition%20Discovery%2FDocuments%2F05%5FLIBERM%5FRefactor%2F02%5FTechnical%2FInstallation%20and%20Configuration&viewid=02f53069%2D2726%2D4cc2%2D9487%2D92795452288a&sharingv2=true&fromShare=true&at=9&CID=c0a10614%2Dd607%2D43ec%2D88f2%2D22604dfe7c7f&FolderCTID=0x012000A0D05FFEA9BC064FA384346FC5479C7B&TeamsCID=93e1d208%2De7e8%2D4930%2D8a6d%2Dd84e8e516ab9&OR=Teams%2DHL&CT=1782987786378

# CREATE drectories:
      Java > LiberM_Refactor > 
      Here extract these 3 ZIP Projects and
      Delete their ZIP version in the end
 
```

Extract the compressed modules into a clean shared directory. The target filesystem layout must mimic the following structure:

```text
D:\Technical\LiberM\COBOLtoJAVA\
├── aws_cards_demo\               # Legacy source files (COBOL, JCL, PROC, BMS)
│   ├── sources\
│   │   ├── cbl\
│   │   ├── jcl\
│   │   ├── proc\
│   │   └── bms\
│   └── logs\                     # Direct extraction error logs
└── nib-java-demo-apps\           # Modernized Target Java/Angular Ecosystem
    ├── aws-carddemo-angular\     # Converted frontend/backend components
    │   ├── app-angular\
    │   ├── app-angular_sources\
    │   ├── src\
    ├── aws-carddemo-app\         # Card demo app
    ├── boot-batch\               # Supernaut transactional batch module
    ├── boot-online\              # Supernaut transactional online module
    ├── config\                   # Configuration profiles (.yaml, .conf)
    ├── deployement\              # Deployment
    └── http\                     # HTTP

```

---

## 5. The LiberM Refactor Process (Code Conversion)

The code migration workflow processes mainframe source artifacts into microservices-ready structures.

### 5.1 Convert COBOL to Java

Mainframe execution pathways vary significantly between batch scripts and interactive screens. You must segregate your source programs during your initial technical analysis phase using **Dahlia**:

* **Batch Programs:** Naming standard ends with `*CB` (e.g., `CUSTFILECB`).
* **Online Programs:** Naming standard ends with `*CO` (e.g., `COSGNC00`).
* **Copybooks:** Isolate and place copybooks within a dedicated subdirectory (`sources\cpy`).

#### Step 5.1.1: Edit `conv_cobol_batch_java_config.xml` & `conv_cobol_online_java_config.xml`

Modify the configuration file mappings to establish appropriate boundaries:

```xml
<resource pattern="CB*" location="sources\cbl" output="${delivery_project_root}\src\main\java" package="com.aws.carddemo.batch.program" />

<converter_settings>
    <setting name="target_syntax" value="JAVA" />
    <setting name="target_os" value="windows" />
    <setting name="target_db" value="pgsql" />
    <setting name="variable_break_char" value="-" />
    <setting name="copybooks_location" value="${workspace}\sources\cpy;${workspace}\sources\cpy-bms" />
</converter_settings>

```

*Note: `${delivery_project_root}` automatically points to your active frontend application directory (`\nib-java-demo-apps\aws-carddemo-angular`).*

#### Step 5.1.2: Run the Conversion Engine

Open an elevated Command Prompt, navigate to the source directory, and trigger the migration script:

```TODO -> cmd
cd D:\Technical\LiberM\COBOLtoJAVA\aws_cards_demo
Convert.bat

```

*(Alternatively, execute `Convert.bat` based on your project configuration module).*

### 5.2 Convert JCL & PROC to Groovy

1. Copy JCL files to `source/jcl` and PROCs to `source/proc`.
2. Review and configure paths in `conv_jcl_groovy_config.xml` and `conv_proc_groovy_config.xml`.
3. Run `run.bat` to translate legacy streams into functional Groovy workflow scripts.

### 5.3 Convert BMS to Angular UI

1. Store raw binary map streams (`.bms`) inside `source/bms`.
2. Configure `conv_bms_angular_config.xml`.
3. Run the conversion engine to output 3 responsive modernization assets:
* **HTML Template** (Structural view markup layout)
* **TypeScript (TS)** (Reactive interface component controller)
* **JSON Map** (Transferred data fields variable block)

```TODO -> Angular Packages
# Navigate directly to the deployment orchestrator directory
cd D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps\deployment\docker-compose

# Build, create, and launch all cluster engines in background detached mode
docker compose up --force-recreate --build -d

```

## 6. Dockerized Runtime Infrastructure

The runtime platform requires isolated background support services (databases, metric logs, data proxies). Ensure **Docker Desktop** is open and active before running container routines.

```TODO -> Replace in app-angular pom.xml
# Replace in app-angular pom.xml
--------------------------------

<arguments>
    install zone.js @angular/animations@21.2.17 ngx-spinner@21.0.0 --save
</arguments>

```

## 7. Building the Converted Project

Once the structural source files have been refactored into clean Java code, compile the application using Apache Maven:


```TODO -> Install Packages using MAVEN Lifecycle

# Build using IntelliJ Idea under MAVEN Lifecycle
------------------------------------------------

    Click one by one:
        Clean
        Package
        Install
```

```
#                    OR
```


```TODO -> commands

# Navigate to the target demo application directory
----------------------------------------------------

# Change Directory
cd D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps

# Execute clean install to build executable JARs and resolve dependencies
mvn clean install

# resolve maven dependencies
mvn dependency:resolve

```

Confirm the compilation output ends with a green **`BUILD SUCCESS`** notification across all modules before proceeding.

---


### Infrastructure Cluster Verification

Open your Docker Desktop dashboard or run `docker ps` to verify the state of your infrastructure:

* **`nib-java-demo-cluster`** (Root transactional context group)
* **`motorhead-a-1 / a-2 / b-1 / b-2`** (Liber Batch processing engines)
* **`grafana-1`** (Telemetry and system monitoring dashboard)
* **`loki-1`** (Centralized log aggregation engine)

### Core Exposed Ports Matrix

| Container Port Mapping | Operational Responsibility |
| --- | --- |
| **`3271`** | TN3270 Terminal Connection Interface |
| **`8090`** | LIBER*TP Web Services Access Layer |
| **`8081`** | LIBER*Batch Engine Proxy Core |
| **`3000`** | Grafana Performance Monitoring Engine |

---

## 8. Running the Application (Online Transactions)

Interactive online transactions can be invoked through two different methods: the traditional TN3270 emulator or a modernized Angular web interface.

### Method A: Traditional TN3270 Emulator Interface

1. Run `Tn3270.exe` on your workstation.
2. Select **Host** $\rightarrow$ **Connect** from the application menu bar and input the following parameters:
* **Host:** `localhost`
* **Port:** `3271`
* **Advance: Terminal Size:** `24 x 80`


3. Once connected, open the menu bar, navigate to **View**, and verify that **Keypad Toolbar** is checked. This displays the emulator keypad layout on your screen.
4. Click the **Clear** button on the floating keypad component to wipe the active buffer screen.
5. Enter the target 4-character CICS Transaction Identifier on the command line: **`CC00`** and press **Enter**.
6. At the sign-on screen, input the system credentials:
* **USER ID:** `user0001`
* **Password:** `password`


7. Press **Enter** to open the functional **Main Menu** panel (Options include Account View, Credit Card List, Bill Payments, etc.).

### Method B: Modernized Browser UI (Angular Interface)

To run the web interface natively, launch both the transactional backend and web presentation contexts from IntelliJ IDEA:

#### 1. Configure the Backend (Boot Online)

* In IntelliJ, click **Edit Configurations...** in the top-right corner.
* Create a new Application profile named **`Boot Online`**.
* Set the SDK to **Java 21** and module target to `boot-online`.
* Main Class: `com.nib.supernaut.SupernautBootApp`
* VM / Program Arguments: `--spring.config.location=file:../config/online/application.yaml`
* Working Directory: `D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps`
* Environment Variables: Include paths pointing to your local `nib-ascii.conf` parameters file.

#### 2. Configure the Frontend (Boot Angular)

* Create another Application profile named **`Boot Angular`**.
* Main Class: `com.aws.carddemo.angular.AwsServiceApplication`
* Program Arguments: `--spring.config.location=file:../config/angular/awsangular.yaml`
* Working Directory: Set directly to the Angular subproject directory: `D:\Technical\LiberM\COBOLtoJAVA\nib-java-demo-apps\aws-carddemo-angular`

#### 3. Run and Access the Web App

* Launch both **`Boot Online`** and **`Boot Angular`** configurations from your IDE.
* Open your browser and navigate to the application endpoint URL defined in your local `awsangular.yaml` configuration file to access the modernized interface.

---

## 9. Running Batch Jobs (Liber Batch / Motorhead)

The `LiberBatch` framework exposes specialized REST API endpoints to trigger and orchestrate converted Groovy job scripts. You can invoke these routines using toolchains like Postman, Curl, or your local terminal browser engine.

### Operational Endpoints Matrix

* **View Completed Jobs:** `GET http://localhost:8081/job/list`
* **View Active Running Processes:** `GET http://localhost:8081/job/ps`
* **Check Individual Job Status:** `GET http://localhost:8081/job/status/{uuid}`
* *Example:* `GET http://localhost:8081/job/status/1c0f058c-865a-4ef7-b0b0-675dd79114f1`



### Submit a New Batch Job

To schedule and trigger a process step, dispatch a `POST` request payload to the job engine runner:

* **Endpoint Address:** `POST http://localhost:8081/job/submit`
* **HTTP Header:** `Content-Type: application/json`
* **JSON Request Body:**

```json
{
  "script": "CUSTFILE.groovy",
  "jobClass": "A",
  "onHold": false,
  "variables": {
    "RUNDATE": "2026-04-17",
    "PREFIX": "BTC001"
  }
}

```

Alternatively, you can trigger jobs directly from your terminal using `curl`:

```bash
curl -X POST -H "Content-Type: application/json" -d "{\"script\": \"CUSTFILE.groovy\",\"jobClass\": \"A\",\"onHold\": false,\"variables\": {\"RUNDATE\": \"2026-04-17\",\"PREFIX\": \"BTC001\"}}" http://localhost:8081/job/submit

```

---

## 10. Troubleshooting & Log Analysis

### 10.1 Refactor/Conversion Phase Troubleshooting

All syntax, processing parsing errors, and warnings encountered during translation are saved locally to individual file summaries within the following directory:
`\aws_cards_demo\logs\`

To generate a consolidated view of these log files, run the automated analysis batch script:

```cmd
cd D:\Technical\LiberM\COBOLtoJAVA\aws_cards_demo
Analyze.bat

```

This tool automatically exports all compilation diagnostic exceptions into a standardized Excel analysis sheet for easier troubleshooting (e.g., `AnalysisReport_30-04-2026_09-33-27.xlsx`).

### 10.2 Runtime Layer Diagnostics

If system exceptions occur or if a service drops in the background:

1. Run `docker ps` to verify that all structural containers are active.
2. Inspect operational telemetry errors by streaming logs directly from the active runtime container:
```bash
docker logs <container-id_or_name> --tail 100 -f

```


3. To simplify transaction log streaming during testing, open **Docker Desktop**, navigate to the **`nib-java-demo-cluster`** stack layout, and inspect the runtime output trace logs inside **`supernaut-1`**.
   *(Pro-tip: Temporarily stop clusters `supernaut-2` and `supernaut-3` while testing to isolate all active execution traces onto this single node container).*

```

***

### 🛠️ Step-by-Step Build Order Checklist for Beginners
1. **Credentials:** Set up your GitHub PAT token inside `C:\Users\<user>\.m2\settings.xml`.
2. **Environment:** Run the PowerShell environment variable registration script, then restart IntelliJ.
3. **Extraction:** Verify your files are arranged according to the layout described in Section 4.
4. **Translation:** Open a terminal window inside `aws_cards_demo` and execute `run.bat` to perform the code conversion.
5. **Compilation:** Open a terminal window inside `nib-java-demo-apps` and run `mvn clean install`.
6. **Infrastructure:** Start your containers by running `docker compose up -d` within the project deployment folder.
7. **Execution:** Launch your transaction processes using either your browser or your TN3270 terminal client.

```"# JAVA_LiberM_Refactor_Angular" 
"# JAVA_LiberM_Refactor_Angular" 
