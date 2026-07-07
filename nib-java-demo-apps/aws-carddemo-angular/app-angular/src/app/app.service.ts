import { Injectable } from'@angular/core';
import { HttpClient, HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, EMPTY, throwError } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { BmsMap } from './app.bmsmap';
import { NgxSpinnerService } from "ngx-spinner";
import { ErrorService } from './error.service';
import { TEMPLATE_REGISTRY } from './template-registry';
import { DEFAULT_TRANSACTION, TRANSACTIONS } from './transaction-registry';

@Injectable({
providedIn: 'root'
})
export class AppService {

	readonly Templates = TEMPLATE_REGISTRY;

	getDeclararedMaps() {
		return Object.entries(this.Templates);
	}

	dashboardName: string = "WELCOME";

	activeComponent: any;

	private componentName: BehaviorSubject<string> = new BehaviorSubject<string>('WELCOME');

	componentName$ = this.componentName.asObservable();

	// Server message (SEND TEXT) or error message
	serverMessage: any = '';

	// Transaction selected on the welcome map
	selectedTransaction: string = DEFAULT_TRANSACTION;

	// Replace with your list of preferred transactions
	readonly transactions = TRANSACTIONS;

	// Actual Map/Json values
	currentResponse: any;

	// Term id
	termId: any;

	// Session id
	sessionId: any;

	constructor(private http: HttpClient, private spinner: NgxSpinnerService, private errorService: ErrorService) {}
	connect(): Observable<any> {
		return this.http.post<any>('api/connect', {}).pipe(
			catchError((err: HttpErrorResponse) => {
				// Backend unreachable (java.net.ConnectException, server down, CORS, network)
				if (err.status === 0) {
				  console.error('ConnectException: backend is unreachable', err);
				} else {
				  console.error(`HTTP error ${err.status}: ${err.message}`, err);
				}
				// Always rethrow so component can handle it
				return throwError(() => err);
			})
		);
	}

	disconnect(): Observable<any> {
		if (!this.termId) {
			return EMPTY;
		}
		const body = {
			requestType: 'Converse',
			termId: this.termId,
			sessionId: this.sessionId,
			order: {
				aid: "DFHENTER"
			}
		};
		return this.http.post('api/disconnect', body)
	}

	sendclear(): Observable<any> {
		const body = {
			requestType: 'Converse',
			termId: this.termId,
			sessionId: this.sessionId,
			order: {
				aid: "DFHCLEAR"
			}
		};
		return this.http.post('api/sendclear', body)
	}

	dashboardCommand(command: string): Observable<any> {
		const body = {
			requestType: 'Converse',
			termId: this.termId,
			sessionId: this.sessionId,
			order: {
				aid: "DFHENTER",
				textData: command.trim().toUpperCase()
			}
		};
		return this.http.post('api/dashboardCommand', body);
	}

	converse(keyValue: string, fields: any[]): Observable<any> {
		const body = {
			requestType: 'Converse',
			termId: this.termId,
			sessionId: this.sessionId,
			order: {
				aid: keyValue,
				fields: fields
			}
		};
		return this.http.post('api/converse', body);
	}

	populateConnectionData() {
		if (this.currentResponse) {
			//console.log(this.currentResponse);
			const termId = document.getElementById("termId");
			if (termId !== null) {
				termId.textContent = "Terminal: " + this.termId;
			}
			const sessionId = document.getElementById("sessionId");
			if (sessionId !== null) {
				sessionId.textContent = "Session: " + this.sessionId;
			}
		}
	}

	populateMap(currentMap: string) {
		//Populate html map with json info received
		//console.log(this.currentResponse);
		this.populateConnectionData();
		if (!this.currentResponse.orders) {
			return;
		}
		//We can represent on the browser only the last order supposing it's a SEND MAP
		const lastOrder = this.currentResponse.orders[this.currentResponse.orders.length - 1];

		if (!lastOrder.fields) {
			return;
		}

		//If not null it's a customized map
		const params = new URLSearchParams(window.location.search);
		let isCustomMap = false;
		for (const key of params.keys()) {
			const look = key.trim().toUpperCase();
        		if (currentMap.endsWith("_" + look)) {
				isCustomMap = true;
				break;
			}
		}

		//Setup <pre> fixed fields
		for (const field of lastOrder.fields) {
			const element = document.getElementById(field.name);
			//console.log(element);
			if (element) {
				if (field.text !== null) {
					let value: string = field.text.trimEnd();
				  if (value.length > 0){
					    element.textContent = value.trimEnd();
							//console.log("Field update " + field.name //
							//	+ ", text = [" + field.text + "]" //
							//	+ ", text_len = [" + value.length + "]" //
				  }
					if (isCustomMap === false) {
						let currentClass: string = element.className;
						let tag: string = " ng-";
						let posStart: number = currentClass.indexOf(tag);
						let angularAttrs: string = "";
						let newClass = currentClass;
						if (posStart > -1) {
							angularAttrs = currentClass.substring(posStart);
							currentClass = currentClass.substring(0, posStart);
							newClass = this.createClass(field, element, currentClass);
						}
						if (currentClass !== newClass) {
							let type: string = this.getFieldType(field, element);
							/* DEBUG
							console.log("New class field " + field.name //
								+ ", type = [" + type + "]" //
								+ ", row = [" + field.row + "]" //
								+ ", column = [" + field.column + "]" //
								+ ", askip = [" + field.askip + "]" //
								+ ", dark = [" + field.dark + "]" //
								+ ", color = [" + field.colour + "]" //
								+ ", extendedHighlight = [" + field.extendedHighlight + "]" //
								+ ", highlight = [" + field.highlight + "]" //
								+ ", prot = [" + field.prot + "]" //
								+ ", text = [" + field.text + "]" //
							);
							console.log("Field " + field.name + ",     class: [" + element.className + "]");
							console.log("Field " + field.name + ", new class: [" + newClass + "]");
							console.log("Field " + field.name + ",  assigned: [" + newClass + angularAttrs + "]");
							*/
							element.className = newClass + angularAttrs;
						}
					}
				}
				if (field.cursor !== null && field.cursor == true) {
					//console.log(`Field '${field.name}' setup cursor `);
					element.focus();
					(document.getElementById(field.name) as HTMLInputElement).select();
				}
			} else {
				if (isCustomMap === false) {
					console.warn(`Element with ID '${field.name}' not found`);
				}
			}
		}

		//Setup <input>/<pre> fields
		if (this.activeComponent) {
			let map: BmsMap = this.activeComponent as BmsMap;
			map.setlastOrderFields(lastOrder.fields);
			//console.info("The active component is = " + map.getMapName());
			for (const field of lastOrder.fields) {
				const control = map.getForm().get(field.name);
				if (control) {
					//console.log("Form field Bef " + field.name + ", value = " + control?.getRawValue());
					//let value: string = field.text;
					const value = ((field.text ?? '') as string).trimEnd();
					// control?.setValue(value.trimEnd());
					// Set even if empty, and avoid formatter loops if needed
					control?.setValue(value, { emitEvent: false });
					//control?.updateValueAndValidity({ emitEvent: false });
					//console.log("Form field Aft " + field.name + ", value = " + control?.getRawValue());
					//queueMicrotask(() => {
					//  console.log('Form field Aft ' + field.name + ', after microtask:', control?.value);
					//});
				}
			}
		}
	}

	createClass(field: any, element: any, currentClass: string): string {
		let rc: string = "";
		//let type: string = this.getFieldType(field, element);

		let prefix: string = "";
		let tag: string = " len";
		let posStart: number = currentClass.indexOf(tag);
		if (posStart > -1) {
			posStart += tag.length;
			let posEnd: number = currentClass.indexOf(" ", posStart);
			prefix = currentClass.substring(0, posEnd + 1);
		} else {
			console.error(field.name + " unexpected class value: " + currentClass);
			return "";
		}

		let isInputOutput: boolean = false;
		tag = " ";
		posStart = prefix.indexOf(tag);
		if (posStart > -1) {
			isInputOutput = true;
		}
		let isDark: boolean = false;
		if (field.dark) {
			isDark = true;
		}
		let color = "defaultfg";
		if (field.colour) {
			/*
				BMS mapping color

				DFHBLUE((char) 0xF1), //   -15
				DFHRED((char) 0xF2), //    -14
				DFHPINK((char) 0xF3), //   -13
				DFHGREEN((char) 0xF4), //  -12
				DFHTURQ((char) 0xF5), //   -11
				DFHYELLO((char) 0xF6), //  -10
				DFHNEUTR((char) 0xF7), //  -9
			*/
			if (field.colour === -15) {
				color = "bluefg";
			} else if (field.colour === -14) {
				color = "redfg";
			} else if (field.colour === -13) {
				color = "pinkfg";
			} else if (field.colour === -12) {
				color = "greenfg";
			} else if (field.colour === -11) {
				color = "turquoisefg";
			} else if (field.colour === -10) {
				color = "yellowfg";
			}
		}
		/*
			EXTENDED_HIGHLIGHT

			DFHBLINK((char) 0xF1), //
			DFHREVRS((char) 0xF2), //
			DFHUNDLN((char) 0xF4), //
		*/
		let isBlink: boolean = false;
		let isReverse: boolean = false;
		let isUnderline: boolean = false;
		if (field.extendedHighlight) {
			//F1
			if (field.extendedHighlight === -15) {
				isBlink = true;
				//F2
			} else if (field.extendedHighlight === -14) {
				isReverse = true;
				//F4
			} else if (field.extendedHighlight === -12) {
				isUnderline = true;
			}
		}
		if (isDark) {
			if (isInputOutput) {
				color += "-drk";
			}
		} else {
			if (isReverse) {
				color += "-reverse";
			}
		}

		let attr: string = "";
		// BRT or NORM or DRK
		if (!isDark) {
			if (field.highlight && field.highlight === true) {
				attr += "brt ";
			} else {
				attr += "norm ";
			}
		}
		if (isBlink) {
			attr += "blink ";
		}
		if (isUnderline) {
			attr += "underline ";
		}
		let isProt: boolean = false;
		if (field.prot && field.prot === true) {
			isProt = true;
			element.setAttribute('readonly', true);
		} else {
			element.removeAttribute('readonly');
		}
		if (isInputOutput) {
			//Switch mapfield to mapfield-prot or the other way round
			let mapField: string = prefix.substring(0, posStart);
			if (mapField === "mapfield") {
				if (isProt) {
					var re = /mapfield/gi;
					prefix = prefix.replace(re, "mapfield-prot");
				}
			} else if (mapField === "mapfield-prot") {
				if (!isProt) {
					var re = /mapfield-prot/gi;
					prefix = prefix.replace(re, "mapfield");
				}
			} else {
				console.error(field.name + " unexpected class value=[" + mapField + "],   class=[" + currentClass + "]");
			}
		}
		attr = attr.trim();
		//let classValue: string = "";
		//classValue += line + " ";
		//classValue += column + " ";
		//classValue += length;
		rc = prefix;
		if (color && color.trim()) {
			rc += color + " ";
		}
		if (attr && attr.trim()) {
			rc += attr + " ";
		}
		//classValue = classValue.trim();
		return rc.trim();
	}

	getFieldType(field: any, element: any): string {
		let rc: string = "Input";
		let isCostant: boolean = false;
		if (field.askip && field.askip === true) {
		  isCostant = true;
		} else {
		  if (element instanceof HTMLPreElement) {
		    isCostant = true;
		  }
		}
		if (isCostant) {
			let value: string = field.text;
			if (!value && !value.trim() || !field.name.trim()) {
				rc = "Output";
			} else {
				rc = "Constant";
			}
		}
		return rc;
	}

  handleConnect(): void {
    this.showloader();

    this.connect()
      .pipe(finalize(() => this.hideloader()))
      .subscribe({
        next: (response: any) => {
          this.currentResponse = response;
          this.termId = response.termId;
          this.sessionId = response.sessionId;
        },
        error: (error) => {
          console.error('Error loading HTML template:', error);
        }
      });
  }

  handleDiconnect(): void {
    this.showloader();

    this.disconnect()
      .pipe(finalize(() => this.hideloader()))
      .subscribe({
        next: (response: any) => {
          this.currentResponse = null;
          this.termId = null;
          this.sessionId = null;
        },
        error: (error) => {
          console.error('Error loading HTML template:', error);
        }
      });
  }

  handleSendclear(): void {
    this.showloader();

    this.sendclear()
      .pipe(finalize(() => this.hideloader()))
      .subscribe({
        next: (response: any) => {
          this.currentResponse = response;
          this.termId = response.termId;
          this.sessionId = response.sessionId;
        },
        error: (error) => {
          console.error('Error loading HTML template:', error);
        }
      });
  }

  handleDashboardCommand(command: string): void {
    this.showloader();

    this.dashboardCommand(command)
      .pipe(finalize(() => this.hideloader()))
      .subscribe({
        next: (response: any) => {
          if (response.orders != null) {
            const error = this.isValidSendMap(response);
            if (error) {
              console.debug("DBG>handleDashboardCommand: server error = " + error);
              try {
                this.handleSendclear();
              } catch (e) {
                console.error(e);
              }
              this.setComponentName(this.dashboardName);
              this.serverMessage = error;
              return;
            }

            this.currentResponse = response;
            const lastOrder = this.currentResponse.orders[this.currentResponse.orders.length - 1];
            this.termId = response.termId;
            this.sessionId = response.sessionId;

            const requestedComponent = this.getCandidateComponentName(response);

            if (this.getComponentName() == requestedComponent) {
              this.populateMap(requestedComponent);
            } else {
              this.setComponentName(requestedComponent);

              if (!this.existsMap(requestedComponent)) {
                try {
                  this.handleSendclear();
                } catch (e) {
                  console.error(e);
                }
                this.setComponentName(this.dashboardName);
                this.serverMessage =
                  "Cannot find map: " + lastOrder.mapName + " of mapset: " + lastOrder.mapsetName;
                return;
              }
            }

            this.serverMessage = response.textData;
            if (response.errorMessage && response.errorMessage.trim().length > 0) {
              this.serverMessage = response.errorMessage;
            }
          }
        },
        error: (error) => {
          console.error('Error loading HTML template:', error);
        }
      });
  }

  handleFunctionKey(keyPressed: string, fields: any): void {
    this.showloader();

    this.converse(keyPressed, fields)
      .pipe(finalize(() => this.hideloader()))
      .subscribe({
        next: (response: any) => {
          if (response.orders != null) {
            const error = this.isValidSendMap(response);
            if (error) {
              console.debug("DBG>handleFunctionKey: server error = " + error);
              try {
                this.handleSendclear();
              } catch (e) {
                console.error(e);
              }
              this.setComponentName(this.dashboardName);
              this.serverMessage = error;
              return;
            }

            this.currentResponse = response;
            const lastOrder = this.currentResponse.orders[this.currentResponse.orders.length - 1];
            this.termId = response.termId;
            this.sessionId = response.sessionId;

            const requestedComponent = this.getCandidateComponentName(response);

            if (this.getComponentName() == requestedComponent) {
              this.populateMap(requestedComponent);
            } else {
              this.setComponentName(requestedComponent);

              if (!this.existsMap(requestedComponent)) {
                try {
                  this.handleSendclear();
                } catch (e) {
                  console.error(e);
                }
                this.setComponentName(this.dashboardName);
                this.serverMessage =
                  "Cannot find map: " + lastOrder.mapName + " of mapset: " + lastOrder.mapsetName;
                return;
              }
            }

            this.serverMessage = response.textData;
            if (response.errorMessage && response.errorMessage.trim().length > 0) {
              this.serverMessage = response.errorMessage;
            }
          }
        },
        error: (error) => {
          console.error('Error loading HTML template:', error);
        }
      });
  }

	isValidSendMap(response: any): string {
		const lastOrder = response.orders[response.orders.length - 1];
		if (!lastOrder.type && lastOrder.mapName?.trim().toUpperCase() === 'WELCOME') {
			return '';
		}
		if (lastOrder?.type == 'SendMap') {
			return '';
		}
		if (lastOrder.type?.trim().toUpperCase() != 'Abend') {
			console.error('Order type not handled: ' + lastOrder.type);
		}
		if (response.errorMessage && response.errorMessage.trim().length > 0) {
			let rc: string = response.errorMessage;
			if (rc?.trim().toUpperCase() === 'ERROR 70 (NOTAUTH) DETECTED') {
				rc = 'USER NOT AUTHORIZED TO PERFORM THIS ACTION';
			}
			return rc;
		}
		let rc: string = 'Unhandled error';
		if (lastOrder.textData && lastOrder.textData.trim().length > 0) {
			rc = rc + ": " + lastOrder.textData;
		}
		return rc;
	}

	getCandidateComponentName(response: any): string {
    		const lastOrder = response.orders[response.orders.length - 1];
		if (lastOrder.mapsetName == null) {
			return lastOrder.mapName;
		}

		const params = new URLSearchParams(window.location.search);

		for (const key of params.keys()) {
				const look = key.trim().toUpperCase();
				const tryCustom = lastOrder.mapsetName + "_" + lastOrder.mapName + "_" + look;
				if (this.existsMap(tryCustom)) {
						//console.log("Found custom map=" + tryCustom);
						return tryCustom;
				}
		}
		return lastOrder.mapsetName + "_" + lastOrder.mapName;
	}

	existsMap(mapName: string): boolean {
		const maps = this.getDeclararedMaps();
		//console.log('existsMap:searching:mapName->(' + mapName + ')');
		for (let i = 0; i < maps.length; i++) {
			const [bmsMap, obj] = maps[i];
			if (bmsMap === mapName) {
				//console.log('existsMap:found:mapName->(' + mapName + ')');
				return true;
			}
			//console.log('existsMap:currentValue:bmsName->' + bmsMap);
		}
		//console.log('existsMap:not found:mapName->(' + mapName + ')');
		return false;
	}

	setComponentName(name: string): void {
		this.componentName.next(name);  // Update the componentName
	}

	// Method to get the current value of componentName
	getComponentName(): string {
		return this.componentName.value;
	}

	showloader() {
		this.spinner.show();
	}

	hideloader() {
		this.spinner.hide();
	}
}
