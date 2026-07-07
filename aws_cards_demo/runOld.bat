@echo off
set NIB_PARANOID_LICENSE_KEY=rxg1bu9TBifBg9UpiL1CHNBwMrkQUkiCntvR7jzu91O/jFLtueTcaUV41rUNhY0V/GDD0PKvmrdqc3t346bRLETlH1GRE/v9N2jSSpEFafhwHRfCQHhwnGoSuHl9SAzxTN+JFyzEMTy4MVF2cD/ZH5nenPSlDc5eDn59cz9Y6PI/6m2Y1SICXEKfBXU7IbX0mmcNu4n00kzYyilvMWRBnEDEgOFLclldV+A1WxKoyPcPFdL4wOIiMinZcLEzGXGMEz9HNpVPEypgB3WbvN7wkQ==
set CTOOL=nib-paranoid-1.7.15.exe
set DG_CONV_WORKSPACE_IN=%cd%
set DG_CONV_WORKSPACE_OUT=%cd%\..\nib-java-demo-apps\aws-carddemo-app
::echo DG_CONV_WORKSPACE_OUT=%DG_CONV_WORKSPACE_OUT%
::dir %DG_CONV_WORKSPACE_OUT%rem paranoid-1.68.exe conv_cobol_online_java_config.xml
::%CTOOL% conv_cobol_batch_java_config.xml
::%CTOOL% conv_cobol_online_java_config.xml
::%CTOOL% conv_bms_angular_config.xml
%CTOOL% conv_jcl_groovy_config.xml
::%CTOOL% conv_proc_groovy_config.xml
