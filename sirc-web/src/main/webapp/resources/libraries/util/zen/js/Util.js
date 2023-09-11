(function( Util, $, undefined ) {

    // Static Classes

    Util.Message = {

        /**
		 * Function to show a error message in the default element
		 * (#page-message-container).
		 * 
		 * @param message
		 */
        error : function (message) {
            $("#page-message-container").html(
                '<div class="alert alert-danger alert-dismissible" role="alert" style="margin-top: 20px;">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                + message + '</div>');
        },
        
        warning : function (message) {
            $("#page-message-container").html(
                '<div class="alert alert-warning alert-dismissible" role="alert" style="margin-top: 20px;">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                + message + '</div>');
        },

        success : function (message) {
            $("#page-message-container").html(
                '<div class="alert alert-success alert-dismissible" role="alert" style="margin-top: 20px;">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                + message + '</div>');
        },

        info : function (message) {
            $("#page-message-container").html(
                '<div class="alert alert-info alert-dismissible" role="alert" style="margin-top: 20px;">' +
                '<button type="button" class="close" data-dismiss="alert" ' +
                'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                + message + '</div>');
        }
        
    };
    
    Util.Message.Modal = {

            /**
			 * Function to show a error message in the default element
			 * (#page-message-container).
			 * 
			 * @param message
			 */
            error : function (message) {
                $("#page-modal-container").html(
                    '<div class="alert alert-danger alert-dismissible" role="alert" style="margin-top: 20px;">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    + message + '</div>');
            },
            
            warning : function (message) {
                $("#page-modal-container").html(
                    '<div class="alert alert-warning alert-dismissible" role="alert" style="margin-top: 20px;">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    + message + '</div>');
            },

            success : function (message) {
                $("#page-modal-container").html(
                    '<div class="alert alert-success alert-dismissible" role="alert" style="margin-top: 20px;">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    + message + '</div>');
            },

            info : function (message) {
                $("#page-modal-container").html(
                    '<div class="alert alert-info alert-dismissible" role="alert" style="margin-top: 20px;">' +
                    '<button type="button" class="close" data-dismiss="alert" ' +
                    'aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    + message + '</div>');
            }
            
    };

	Util.Validation = {

		/**
		 * Checks if the value is an integer.
		 * 
		 * @param value
		 * @returns {boolean}
		 */
		isInteger : function (value) {
			return !isNaN(value) && (function(x) { return (x | 0) === x; })(parseFloat(value));
		}

	};

	Util.String = {

		/**
		 * Escapes the characters &lt; and &gt;.
		 * 
		 * @param text
		 * @returns {string}
		 */
		escapeGtAndLt : function (text) {
			return text.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		},

		/**
		 * Escapes the characters &amp;, &lt;, &gt;, &quot; and &#039;.
		 * 
		 * @param html
		 * @returns {string}
		 */
		escapeHtml : function (html) {
			return html
				.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
				.replace(/"/g, "&quot;").replace(/'/g, "&#039;");
		}

	};

	Util.Time = {

		/**
		 * 
		 * @param timestamp
		 */
		convertToClientTimestamp : function (timestamp) {
			var timezoneOffset = $("#variables").data("server-timezone-offset");
			return moment(timestamp + " " + timezoneOffset, 'YYYY-MM-DD HH:mm:ss ZZ')
				.format("YYYY-MM-DD HH:mm:ss").toString();
		},

		obtainClientsTimezone : function () {
			return moment().format('Z');
		}
		
	};
	
	Util.Date = {
			
		getFormatDate : function () {
			return 'DD/MM/YYYY';
		}
	};
	
	Util.ObjectJson = {
			
			getObjectJson : function (form) {
				
				var formatDate = Util.Date.getFormatDate();
				var jsonData = '{';
		    	var map = new Object();
		    	
		    	$('#'+form).each(function(){
		    		var selects = $(this).find('select option:selected');
		    		selects.each(function(){
		    			var select = $(this);
		    			
		    			if (jsonData === '{') {
		    				jsonData += '';
						}else{
							jsonData += ',';
						}
		    			
		    			jsonData += '"'+select.parent().attr('name')+'":"'+select.val()+'"';
// console.log(select.parent().attr('name') + ' ' + select.val());
		    		});
		    		
		    		var textarea = $(this).find('textarea');
		    		
		    		textarea.each(function(){
		    			if (jsonData === '{') {
		    				jsonData += '';
						}else{
							jsonData += ',';
						}
		    			jsonData += '"'+$(this).attr('name')+'":"'+$(this).val()+'"';
		    	    });
		    		
		    		var inputs = $(this).find(':input');
		    		inputs.each(function(){
		    			
		    			var input = $(this);
		    			var type  = input.attr('type');
		    			var name = input.attr('name');
// console.log(name + ' ' + input.val() + ' ' + type);
			    		if (type === 'text' || type === 'hidden' || type === 'checkbox'|| type === 'number') {
			    			
			    			if(typeof name !== 'undefined'){
			    				if (name.indexOf(".") >= 0 || type === 'checkbox') {
				    				var mapKey = input.attr('name').split('.');
				    				var key = mapKey[0];
				    				
				    				if (type === 'checkbox') {
				    					if (typeof map[key] === "undefined" || map[key] === '') {
				    						map[key] = '{';
				    					}else{
				    						map[key] += '},{';
				    					}

				    				}else{
				    					var subName = mapKey[1];
				    					var subValue = input.val();
				    					
				    					if (subName.indexOf("fecha") >= 0){
				    						if (subValue.length > 10) {
				    							subValue = input.val().substring(0,10);
				    							subValue = subValue.replace(/-/g, '/');
											}
				    						subValue = moment(subValue, formatDate);
				    					}
				    					
				    					var lastChar = map[key].substr(map[key].length - 1);
					    				if (map[key] === '{' || lastChar === '{') {
					    					map[key] += '"'+mapKey[1]+'":"'+subValue+'"';
										}else{
											map[key] += ',';
											map[key] += '"'+mapKey[1]+'":"'+subValue+'"';
										}
									}
								}else{
									if (type === 'text' || type === 'hidden'|| type === 'number') {
										
										if (jsonData === '{') {
						    				jsonData += '';
										}else{
											jsonData += ',';
										}
										
										if (name.indexOf("fecha") >= 0){
											var fecha = moment(input.val(), formatDate);
											jsonData += '"'+name+'":"'+fecha+'"';
										}else{
											jsonData += '"'+name+'":"'+input.val()+'"';
										}
									}
								}
			    			}
						}
		    		});
		    	});
		    	
		    	$.each(map, function(index,value){
// console.log("Index = " + index + " value = " + value);
		    		 if (typeof name !== 'undefined') {
		    			if (value !== '') {
		    				 value += '}'; 
		    				 if (jsonData === '') {
		    					jsonData += '"'+index+'":[' + value + ']';
		    				 }else{
		    					jsonData += ',"'+index+'":[' + value + ']';
		    				 }
		    				 
		 				}
					 }
		    	});

		    	jsonData += '}'; 
// console.log(jsonData);
				
		    	return jsonData;
			}
	};

    // Namespaces

    (function (searchcomponent, $, undefined) {

	    /**
		 * This builder facilitates the creation and configuration of a
		 * {@link searchcomponent.SearchComponent} object. Please check out the
		 * allowed configuration for it.
		 * 
		 * @param {jQuery}
		 *            $containerElement
		 * @param {string}
		 *            restServiceUrl
		 * @param {function}
		 *            convertObjectToRowArray This function receives an object
		 *            that needs to be converted to an Array of
		 *            {@link searchcomponent.Row}.
		 * @constructor
		 */
	    searchcomponent.SearchComponentBuilder = function ($containerElement, restServiceUrl, 
	                                                       convertObjectToRowArray) {

		    // Private properties
		    var self = this;
		    var searchComponent = new searchcomponent.SearchComponent($containerElement, 
			    restServiceUrl, convertObjectToRowArray);

		    // Public functions

		    /**
			 * 
			 * @param {searchcomponent.EventListener}
			 *            eventListener
			 * @returns {searchcomponent.SearchComponentBuilder}
			 */
		    this.addEventListener = function (eventListener) {
			    searchComponent.addEventListener(eventListener);
			    return self;
		    };

		    /**
			 * @returns {searchcomponent.SearchComponent}
			 */
		    this.build = function () {
			    return searchComponent;
		    };
		    
	    };

	    /**
		 * It is required that the next elements exist: .sc-search-input,
		 * .sc-search-btn, .sc-settings-btn, .sc-settings, #sc-rows-per-page,
		 * .sc-table, th.sortable (column sorting) tbody, .sc-previous-btn,
		 * .sc-next-btn and .sc-ajax-loader.
		 * 
		 * @param {jQuery}
		 *            $containerElement
		 * @param {string}
		 *            restServiceUrl
		 * @param {function}
		 *            convertObjectToRowArray This function receives an object
		 *            that needs to be converted to an Array of
		 *            {@link searchcomponent.Row}.
		 * @constructor
		 */
	    searchcomponent.SearchComponent = function ($containerElement, restServiceUrl, 
	                                                convertObjectToRowArray) {

		    // private constants
			var DATETIMEPICKER_CONFIG = { format: 'YYYY-MM-DD HH:mm:ss', ignoreReadonly: true, 
				locale: $("#variables").data("locale"), showClear: true, showClose: true };

		    // Private properties
		    var self = this;
		    var page = 0;
		    var currentRowsPerTable = 0;
		    var ajaxRunning = false;
		    var jqXHR = null;
		    var $searchInput = $containerElement.find(".sc-search-input");
		    var $searchBtn = $containerElement.find(".sc-search-btn");
		    var $eraseBtn = $containerElement.find(".sc-erase-btn");
		    var $settingsBtn = $containerElement.find(".sc-settings-btn");
		    var $settings = $containerElement.find(".sc-settings");
		    var $rowsPerPage = $containerElement.find(".sc-rows-per-page");
		    var $tbody = $containerElement.find("tbody");
		    var $previousBtn = $containerElement.find(".sc-previous-btn");
		    var $nextBtn = $containerElement.find(".sc-next-btn");
		    var $ajaxLoader = $containerElement.find(".sc-ajax-loader");
		    var tableHeaderSortQueue = [];
		    var $focusedElement = null;
		    var eventListenerArray = [];
		    var callbackObject = null;
		    var parameterObjectArray = [];

		    // Initialization

		    (function () {

			    $searchInput.keyup(keyupEnterEvent);
			    $searchBtn.click(function (e) {
				    page = 0;
				    self.ajax();
			    });
			    $eraseBtn.click(eraseFields);
			    $settingsBtn.click(function (e) {
				    $settings.toggleClass("hidden");
			    });
			    // Configure boolean clear buttons
			    $containerElement.find('.sc-boolean-control').each(function (index, element) {
				    var $control = $(this); 
				    $control.find('.sc-boolean-clear').click(function (e) {
					    $control.find('input').prop('checked', false);
				    })
			    });
			    
			    $containerElement.find('th.sortable').each(function (index, element) {
				    var po = new searchcomponent.ParameterObject($(element).data('parameter'), 
					    $(element).data('type'));
				    parameterObjectArray.push(po);
			    });

			    // Configure each parameterObject and register the enter key
				// event for the parameters fields
			    for(var i=0; i<parameterObjectArray.length; i++) {
				    var parameterObject = parameterObjectArray[i];

				    if ( parameterObject.type == Util.searchcomponent.ParameterObject.NUMBER_TYPE ) {

					    parameterObject.startElement = $containerElement.find(".sc-" +
						    parameterObjectArray[i].name + "-parameter-start").keyup(keyupEnterEvent);
					    parameterObject.endElement = $containerElement.find(".sc-" +
						    parameterObjectArray[i].name + "-parameter-end").keyup(keyupEnterEvent);
				    }
				    else if( parameterObject.type == Util.searchcomponent.ParameterObject.DATE_TYPE ) {

					    parameterObject.startElement = $containerElement.find(".sc-" +
						    parameterObjectArray[i].name + "-parameter-start-cont")
						    .datetimepicker(DATETIMEPICKER_CONFIG);

					    parameterObject.endElement = $containerElement.find(".sc-" +
						    parameterObjectArray[i].name + "-parameter-end-cont")
						    .datetimepicker(DATETIMEPICKER_CONFIG);
				    }
				    else if( parameterObject.type == Util.searchcomponent.ParameterObject.BOOLEAN_TYPE ) {
					    parameterObject.element = $containerElement.find('.sc-boolean-control.sc-bc-'
						    + parameterObjectArray[i].name);
				    }
				    else if ( parameterObject.type == Util.searchcomponent.ParameterObject.STRING_TYPE ) {
					    parameterObject.element = $containerElement.find(".sc-" +
						    parameterObjectArray[i].name + "-parameter").keyup(keyupEnterEvent);
				    }
			    }
			    
			    // Register listener in table headers for sorting.
			    $containerElement.find("th.sortable").click(toggleSort);

			    $previousBtn.click(function (e) {
				    if( page > 0 ) {
					    page--;
					    self.ajax();
				    }
			    });
			    $nextBtn.click(function (e) {
				    if( currentRowsPerTable == $rowsPerPage.val() ) {
					    page++;
					    self.ajax();
				    }
			    });

		    })();

		    // Private functions

			// TODO: Erase column sort too
		    function eraseFields() {
			    $searchInput.val("");
			    $rowsPerPage.val(10);
			    for(var i=0; i<parameterObjectArray.length; i++) {
				    var parameter = parameterObjectArray[i];
				    if( parameter.type == Util.searchcomponent.ParameterObject.NUMBER_TYPE ) {
					    parameter.startElement.val("");
					    parameter.endElement.val("");
				    }
					else if( parameter.type == Util.searchcomponent.ParameterObject.DATE_TYPE ) {
						parameter.startElement.find("input").val("");
						parameter.endElement.find("input").val("");
					}
				    else if( parameter.type == Util.searchcomponent.ParameterObject.BOOLEAN_TYPE ) {
					    parameter.element.find('input').prop('checked', false);
				    }
				    else {
					    parameter.element.val("");
				    }
			    }
		    }
		    
		    function keyupEnterEvent(e) {
			    if( e.keyCode == 13 ) {
				    page = 0;
				    self.ajax();
			    }
		    }

		    function updateTable(rowsArray) {

			    var trs = "";
			    for(var i=0; i<rowsArray.length; i++) {
				    var row = rowsArray[i];
				    var tr = '<tr ' + (row.cssClass?'class="' + row.cssClass + '"':'') + '>';
				    for(var j=0; j<row.cellArray.length; j++) {
					    var cell = row.cellArray[j];
					    tr += '<td ' + (cell.cssClass?'class="' + cell.cssClass + '"':'')
						    + '>' + (cell.value?cell.value:'') + '</td>';
				    }
				    tr += "</tr>";
				    trs += tr;
			    }
			    $tbody.html(trs);
			    callListeners();

			    currentRowsPerTable = rowsArray.length;

			    $previousBtn.prop("disabled", page==0);
			    $nextBtn.prop("disabled", currentRowsPerTable < $rowsPerPage.val());

			    // Add listeners
			    for(var k=0; k<eventListenerArray.length; k++) {
				    var eventListener = eventListenerArray[k];
				    $tbody.find(eventListener.cssSelector).on(eventListener.eventName,
					    null, eventListener, eventListener.callbackFunction);
			    }

		    }

		    // TODO: Maintain arrow and number in the same line
		    function toggleSort(e) {
				var CARET_UP_HTML = '<span data-sort-type="asc" class="dropup arrow"><span class="caret"></span></span>';
				var CARET_DOWN_HTML = '<span data-sort-type="desc" class="caret arrow"></span>';
				
			    if( ajaxRunning ) {
				    return;
			    }
				
			    console.log(this);
			    var $th = $(this);
			    console.log("toggle sort by " + $th.data("parameter"));

			    var $arrowSpan = $th.find("span.arrow");

			    if( $arrowSpan.size() == 0 ) {
				    tableHeaderSortQueue.push($th);
				    $th.append(CARET_UP_HTML);
			    }
			    else if( $arrowSpan.data("sort-type") == "asc" ) {
				    $arrowSpan.remove();
					$th.append(CARET_DOWN_HTML);
			    }
				else if( $arrowSpan.data("sort-type") == "desc" ) {
					// remove from queue
					for(var i=0; i<tableHeaderSortQueue.length; i++) {
						var $thAux = tableHeaderSortQueue[i];
						if( $thAux.data("parameter") == $th.data("parameter") ) {
							tableHeaderSortQueue.splice(i, 1);
						}
					}
					$arrowSpan.remove();
					$th.find('span.number').remove();
				}
				
				// Update column sort number
				for(i=0; i<tableHeaderSortQueue.length; i++) {
					$thAux = tableHeaderSortQueue[i];
					$thAux.find('span.number').remove();
					$thAux.append('<span class="number">' + (i+1) + '</span>');
				}

			    page = 0;
			    self.ajax();
		    }

		    function callListeners() {
			    for(var k=0; k<eventListenerArray.length; k++) {
				    var eventListener = eventListenerArray[k];
				    eventListener.callbackFunction();
			    }
		    }

		    // Public functions

		    /**
			 * @param {searchcomponent.EventListener}
			 *            eventListener
			 */
		    this.addEventListener = function (eventListener) {
			    eventListenerArray.push(eventListener);
		    };

		    this.ajax = function () {
			    if( ajaxRunning ) {
				    return;
			    }
			    ajaxRunning = true;
			    
			    // Get focused element
			    $focusedElement = $containerElement.find(":focus");

			    // Disable buttons
			    $searchInput.prop("disabled", true);
			    $searchBtn.prop("disabled", true);
			    $previousBtn.prop("disabled", true);
			    $nextBtn.prop("disabled", true);

			    $ajaxLoader.removeClass("hidden");

			    // Prepare request parameters and disable fields
			    var data = {
				    page: page,
				    rowsPerPage: $rowsPerPage.val(),
				    search: $searchInput.val()
			    };
			    for(var i=0; i<parameterObjectArray.length; i++) {
				    var parameter = parameterObjectArray[i];
				    if( parameter.type == Util.searchcomponent.ParameterObject.NUMBER_TYPE ) {

					    parameter.startElement.prop("disabled", true);
					    data[parameter.name + "Start"] = parameter.startElement.val();
					    parameter.endElement.prop("disabled", true);
					    data[parameter.name + "End"] = parameter.endElement.val();
				    }
					else if( parameter.type == Util.searchcomponent.ParameterObject.DATE_TYPE ) {
						parameter.startElement.data("DateTimePicker").disable();
						parameter.startElement.find("span").removeClass("white");
						data[parameter.name + "Start"] = parameter.startElement.find("input").val();
						parameter.endElement.data("DateTimePicker").disable();
						parameter.endElement.find("span").removeClass("white");
						data[parameter.name + "End"] = parameter.endElement.find("input").val();
					}
				    else if( parameter.type == Util.searchcomponent.ParameterObject.BOOLEAN_TYPE ) {
					    parameter.element.find('input').prop("disabled", true);
					    var value = parameter.element.find('input:checked').val();
					    if( value !== undefined ) {
						    data[parameter.name] = value;
					    }
					    else {
						    data[parameter.name] = "";
					    }
				    }
				    else {
					    parameter.element.prop("disabled", true);
					    data[parameter.name] = parameter.element.val();
				    }
			    }
			    // Add sort parameters
			    for(i=0; i<tableHeaderSortQueue.length; i++) {
				    var $th = tableHeaderSortQueue[i];
				    
				    var sortType = "";
				    var $span = $th.find("span");
				    if( $span.size() > 0 ) {
					    sortType = $span.data("sort-type");
				    }
				    data[$th.data("parameter") + "SortType"] = sortType + "," + (i+1);
			    }

			    jqXHR = Util.Ajax.do({
					    url: restServiceUrl,
					    data: data,
						timeout: 20000,
					    type: "GET"
				    },
				    // doneOkCallback
				    function (data) {

					    var rowsArray = convertObjectToRowArray(data.object);
					    updateTable(rowsArray);

					    if( callbackObject && callbackObject.doneOkCallback ) {
						    callbackObject.doneOkCallback(data);
					    }
				    },
				    // doneNoSessionErrorCallback
				    ( (callbackObject && callbackObject.doneNoSessionErrorCallback) ?
					    callbackObject.doneNoSessionErrorCallback : null),
				    // doneErrorCallback
				    ( (callbackObject && callbackObject.doneErrorCallback) ?
					    callbackObject.doneErrorCallback : null),
				    // failCallback
				    ( (callbackObject && callbackObject.failCallback) ?
					    callbackObject.failCallback : null),
				    // alwaysCallback
				    function () {
					    jqXHR = null;
					    ajaxRunning = false;

					    // Enable buttons
					    $searchInput.prop("disabled", false);
					    $searchBtn.prop("disabled", false);

					    // Enable fields
					    for(var i=0; i<parameterObjectArray.length; i++) {
						    var parameter = parameterObjectArray[i];
						    if( parameter.type == Util.searchcomponent.ParameterObject.NUMBER_TYPE ) {

							    parameter.startElement.prop("disabled", false);
							    parameter.endElement.prop("disabled", false);
						    }
							else if ( parameter.type == Util.searchcomponent.ParameterObject.DATE_TYPE ) {
								parameter.startElement.data("DateTimePicker").enable();
								parameter.startElement.find("span").addClass("white");
								parameter.endElement.data("DateTimePicker").enable();
								parameter.endElement.find("span").addClass("white");
							}
						    else if ( parameter.type == Util.searchcomponent.ParameterObject.BOOLEAN_TYPE ) {
							    parameter.element.find('input').prop("disabled", false);
						    }
						    else {
							    parameter.element.prop("disabled", false);
						    }
					    }

					    $ajaxLoader.addClass("hidden");
					    
					    // Set previous focused element
					    if( $focusedElement ) {
						    $focusedElement.focus();
						    $focusedElement = null;
					    }

					    if( callbackObject && callbackObject.alwaysCallback ) {
						    callbackObject.alwaysCallback();
					    }
				    }
			    );

		    };

		    this.cancelAjax = function () {
			    if( jqXHR ) {
				    jqXHR.abort();
			    }
		    };

		    this.reset = function () {
			    page = 0;
			    eraseFields();
			    $tbody.html("");
			    $previousBtn.prop("disabled", true);
			    $nextBtn.prop("disabled", true);
		    };

	    };

	    /**
		 * @param {string}
		 *            name
		 * @param {string}
		 *            type
		 * @constructor
		 */
	    searchcomponent.ParameterObject = function (name, type) {
		    this.name = name;
		    this.type = type;
		    this.element = null;
		    this.startElement = null;
		    this.endElement = null;
	    };
	    /**
		 * ParameterObject type constant for number.
		 * 
		 * @type {string}
		 */
	    searchcomponent.ParameterObject.NUMBER_TYPE = "NUMBER";
	    /**
		 * ParameterObject type constant for date.
		 * 
		 * @type {string}
		 */
	    searchcomponent.ParameterObject.DATE_TYPE = "DATE";
	    /**
		 * ParameterObject type constant for string.
		 * 
		 * @type {string}
		 */
	    searchcomponent.ParameterObject.STRING_TYPE = "STRING";
	    /**
		 * ParameterObject type constant for boolean.
		 * 
		 * @type {string}
		 */
	    searchcomponent.ParameterObject.BOOLEAN_TYPE = "BOOLEAN";
	    
	    /**
		 * 
		 * @param {searchcomponent.Cell[]}
		 *            cellArray
		 * @param {string}
		 *            [cssClass]
		 * @constructor
		 */
	    searchcomponent.Row = function (cellArray, cssClass) {
		    this.cellArray = cellArray;
		    this.cssClass = cssClass;
	    };

	    /**
		 * 
		 * @param {string}
		 *            value
		 * @param {string}
		 *            [cssClass]
		 * @constructor
		 */
	    searchcomponent.Cell = function (value, cssClass) {
		    this.value = value;
		    this.cssClass = cssClass;
	    };

	    /**
		 * 
		 * @param {string}
		 *            eventName
		 * @param {string}
		 *            cssSelector
		 * @param {function}
		 *            callbackFunction This function is call when the specified
		 *            event is fired or when the table is cleared. Also, the
		 *            parameter it recives, is optional.
		 * @constructor
		 */
	    searchcomponent.EventListener = function (eventName, cssSelector, callbackFunction) {
		    this.eventName = eventName;
		    this.cssSelector = cssSelector;
		    this.callbackFunction = callbackFunction;
	    };

	    /**
		 * 
		 * @param {function}
		 *            [doneOkCallback]
		 * @param {function}
		 *            [doneNoSessionErrorCallback]
		 * @param {function}
		 *            [doneErrorCallback]
		 * @param {function}
		 *            [failCallback]
		 * @param {function}
		 *            [alwaysCallback]
		 * @constructor
		 */
	    searchcomponent.CallbackObject = function (doneOkCallback, doneNoSessionErrorCallback,
	                                               doneErrorCallback, failCallback, alwaysCallback) {

		    this.doneOkCallback = doneOkCallback;
		    this.doneNoSessionErrorCallback = doneNoSessionErrorCallback;
		    this.doneErrorCallback = doneErrorCallback;
		    this.failCallback = failCallback;
		    this.alwaysCallback = alwaysCallback;

	    };

    })(Util.searchcomponent = Util.searchcomponent || {}, $);

    (function(Ajax, $, undefined){

        // Public properties

        /**
		 * Status code Ok.
		 * 
		 * @type {number}
		 */
        Ajax.CODE_OK = 0;
        /**
		 * Status code no session.
		 * 
		 * @type {number}
		 */
        Ajax.CODE_SESSION_ERROR = -1;
        /**
		 * Status code Error.
		 * 
		 * @type {number}
		 */
        Ajax.CODE_ERROR = -2;
        /**
		 * Message to show when there is an ajax error.
		 * 
		 * @type {string}
		 */
        Ajax.GENERIC_ERROR_MESSAGE = "There was an unexpected error. Please contact the server administrator.";

        /**
		 * Ajax function with the common functionality.
		 * 
		 * @param {object}
		 *            settings
		 * @param {function}
		 *            [doneOkCallback] Function called when the request was
		 *            successfully. It receives a data parameter.
		 * @param {function}
		 *            [doneNoSessionErrorCallback] Function called when the
		 *            request was successfully, but there was a session error.
		 *            It receives a data parameter.
		 * @param {function}
		 *            [doneErrorCallback]
		 * @param {function}
		 *            [failCallback]
		 * @param {function}
		 *            [alwaysCallback]
		 * @returns jqXHR
		 */
        Ajax.do = function (settings, doneOkCallback, doneNoSessionErrorCallback, doneErrorCallback,
                            failCallback, alwaysCallback) {

            return $.ajax(settings).
            done(function (data, textStatus, jqXHR) {
                console.log("*** done");

                try {
                    if( typeof data != "object") {
                        data = JSON.parse(data);
                    }
                    console.log(data);

                    if( data.code == Util.Ajax.CODE_OK ) {
                        if( doneOkCallback ) {
                            doneOkCallback(data);
                        }
                    }
                    else if ( data.code == Util.Ajax.CODE_SESSION_ERROR ) {
                        if( doneNoSessionErrorCallback ) {
                            doneNoSessionErrorCallback(data);
                        }
                        else {
                            defaultRedirectToHome(data.description)
                        }
                    }
                    else if( data.code == Util.Ajax.CODE_ERROR ) {
                        if( doneErrorCallback ) {
                            doneErrorCallback(data);
                        }
                        else {
                            defaultFailCallback(data.description);
                        }
                        
                    }
                    else {
                        throw "Unknown flow";
                    }
                } catch (e) {
                    console.log("*** exception in done");
                    console.log(e);
                    if( failCallback ) {
                        failCallback(e);
                    }
                    else {
                        defaultFailCallback(Util.Ajax.GENERIC_ERROR_MESSAGE);
                    }
                }

            }).
            fail(function (jqXHR, textStatus, errorThrown) {
                console.log("*** fail");
                console.log(textStatus);
                if( textStatus !== "abort" ) {
                    console.log(errorThrown);

                    try {
                        if( failCallback ) {
                            failCallback(errorThrown);
                        }
                        else {
                            defaultFailCallback(Util.Ajax.GENERIC_ERROR_MESSAGE);
                        }
                    } catch (e) {}
                }
            }).
            always(function () {
                console.log("*** always");

                if( alwaysCallback ) {
                    alwaysCallback();
                }
            });
            
            function defaultFailCallback(message) {
                Util.Message.error(message);
            }

            function defaultRedirectToHome(message) {
                var url = $("title").data("ctx") + "/";
                var form = $('<form action="' + url + '" method="post">' +
                    '<input type="hidden" name="errorMessage" value="' + message + '" />' +
                    '</form>');
                $('body').append(form);
                form.submit();
            }
        };

    })(Util.Ajax = Util.Ajax || {}, $);

}( window.Util = window.Util || {}, jQuery ));