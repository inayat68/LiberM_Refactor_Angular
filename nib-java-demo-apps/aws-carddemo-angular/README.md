# Angular set-up

This project was generated using the following versions:

[Node.js](https://nodejs.org) version 22.14.0
[Angular CLI](https://github.com/angular/angular-cli) version 21.0.0.
[NgxSpinner](https://www.npmjs.com/package/ngx-spinner) version 21.0.0

## Install Angular local node

> **TO BE EXECUTED ONLY THE FIRST TIME THE APP IS CREATED**

- cd PROJECT_DIR

download and install the nodejs version 22.14.0 `from https://nodejs.org/`

afterward use the following command to install the Angular client v21.0.0:

- run `npm install -g @angular/cli@21.0.0`

use the following command to create the app node:

- run `ng new app-angular`

answer as follows to the questions:

? Which stylesheet format would you like to use? (Use arrow keys)                   
❯ CSS             [ https://developer.mozilla.org/docs/Web/CSS                     ]
Sass (SCSS)     [ https://sass-lang.com/documentation/syntax#scss                ]
Sass (Indented) [ https://sass-lang.com/documentation/syntax#the-indented-syntax ]
Less            [ http://lesscss.org                                             ]

CSS -> ENTER

? Do you want to enable Server-Side Rendering (SSR) and Static Site Generation (SSG/Prerendering)?

N -> ENTER

? Which AI tools do you want to configure with Angular best practices? https://angular.dev/ai/develop-with-ai
❯◉ None
 ◯ Agents.md      [ https://agents.md/                                               ]
 ◯ Claude         [ https://docs.anthropic.com/en/docs/claude-code/memory            ]
 ◯ Cursor         [ https://docs.cursor.com/en/context/rules                         ]
 ◯ Gemini         [ https://ai.google.dev/gemini-api/docs                            ]
 ◯ GitHub Copilot [ https://code.visualstudio.com/docs/copilot/copilot-customization ]
 ◯ JetBrains AI   [ https://www.jetbrains.com/help/junie/customize-guidelines.html   ]

None -> ENTER

- cd PROJECT_DIR\\app-angular

use the following command to assign an initial version:

- run `npm version 1.0.0`

use the following command to install the latest devkit package required for building the node:

- run `npm install -D @angular-devkit/build-angular@^21.0.0`
- run `npm install zone.js --save`
- run `npm ci`

use the following command to install the latest NgxSpinner client:

- run `npm install ngx-spinner@21.0.0`

Optionally for integrating with an authorization provider:

- run `npm install angular-oauth2-oidc@21.0.0`

## Update the source directory

- cd PROJECT_DIR

### Windows:
- robocopy app-angular_sources\ app-angular\ /E

### Linux:
- cp -Rf app-angular_sources/* app-angular/

## Build & Deploy locally

Run `mvn package` to build the project. The package artifacts will be stored in the `app-angular/` directory.

## Start the angular service

Start the service using the sample run: AngularNode

## Useful notes

To update an existing Angular installation to the latest one:

cd PROJECT_DIR\\app-angular

ng update @angular/core@latest @angular/cli@latest --allow-dirty
npm outdated
npm update

## Build & Run in docker

- cd PROJECT_DIR

Run `mvn package` to build the project. The jar package `nibservice.jar` will be generated in `target/` directory.

### Build the docker image

docker build -t pharma_pot_angular_image .

### Run the docker container

docker run -d -p 8088:8088 --name pharma_pot_angular pharma_pot_angular_image

### Test the service using aweb browser and connecting to the following url:

http://localhost:8088/nibservice/
