AJS.toInit(function ($) {
		
	AJS.Portal = (function () {
		
		var res = {

            contextPath: function() {
                return AJS.Portal.Data.get("context-path");
            },

            createDialog: function (id, size, title, button, styleClass, content) {
					/*
					 * Available size types: small, medium, large, xlarge 
					 */
					
					html = $([
					          '<section role="dialog" id="' + id + '" class="aui-layer aui-dialog2 aui-dialog2-' + size + ' ' + styleClass + '" aria-hidden="true">',
					          '		<header class="aui-dialog2-header">',
					          '			<h2 class="aui-dialog2-header-main">' + title + '</h2>',
					          /*'		<div class="aui-dialog2-header-actions">',
					          '				<button id="demo-header-action" class="aui-button">Header Action</button>',
					          '			</div>',
					          '			<div class="aui-dialog2-header-secondary">',
					          '				<input id="demo-search" type="text" name="search">',
					          '			</div>',*/
					          '			<a class="aui-dialog2-header-close">',
					          '				<span class="aui-icon aui-icon-small aui-iconfont-close-dialog">Cancel</span>',
					          '			</a>',
					          '		</header>',
					          '		<div class="aui-dialog2-content">',
					          '			' + content,
					          '		</div>',
					          '		<footer class="aui-dialog2-footer">',
					          '			<div class="aui-dialog2-footer-actions">',
					          '				<button id="portal-dialog-confirm-button" class="aui-button aui-button-primary">' + button + '</button>',
					          '				<button id="portal-dialog-close-button" class="aui-button aui-button-link">Cancel</button>',
					          '			</div>',
					          /*'		<div class="aui-dialog2-footer-hint">this is a hint</div>',*/
					          '		</footer>',
					          '</section>' 
					         ].join(""));
					
					AJS.$('body').append(html);
			}
		};
		
		return res;
	})();

    AJS.Portal.Data = (function () {

        var res = {
            props: new Object(), // HashMap<String, Object>

            get: function (propName) {
                if (!this.props[propName]) {
                    name = "portal-" + propName;
                    value = (AJS.$("meta[name='" + name + "']").attr('content') || "");
                    this.set(propName, value);
                    return value;
                }

                return this.props[propName];
            },

            set: function (propName, propValue) {
                this.props[propName] = propValue;
            }
        };

        return res;
    })();

});

AJS.$(document).ready(function () {
	
	console.log("ContextPath: " + AJS.Portal.contextPath());

	AJS.$('a.portal-dialog').map(function() {
		AJS.$(this).click(function(e) {
			AJS.preventDefault(e);
		    
		    element = AJS.$(this);
		    url = element.attr('href');
		    redir = "redir=" + window.location.pathname.replace(AJS.Portal.contextPath(), '');
		    
		    queryParams = url.split('?')[1];
		    
		    if (queryParams != undefined) {
		    	url += "&" + redir;
		    	
		    } else {
		    	url += "?" + redir;
		    }
		    
		    console.log("URL: " + url);
		    
		    AJS.$.get(url, function(response) {
		        AJS.$('#portal-dialog').remove();
		        
		        header = element.find('span');
		        
		        if (!AJS.$.trim(header.html())) { // isEmpty
		            header = element;
		        }
		        
		        button = "Submit";
		        
			    if (element.attr('data-dialog-button')) {
		        	button = element.attr('data-dialog-button');
		        }
			    
		        width = 600;
		        
		        if (element.attr('data-dialog-width')) {
		        	width = element.attr('data-dialog-width');
		        } 
		        
		        height = 400;
		        
		        if (element.attr('data-dialog-height')) {
		        	height = element.attr('data-dialog-height');
		        }

		    	var popup = new AJS.Dialog(width, height, 'portal-dialog');
		        popup.addHeader(header.html());
		        popup.addPanel('Dialog', response);
		        
		        if (element.hasClass('portal-dialog-submit')) {
			        popup.addSubmit(button, function () { AJS.$(".dialog-panel-body form:first").submit(); });
		        }
		        
		        popup.addButton('Close', function (dialog) { dialog.hide(); }, 'portal-dialog-cancel-button');
		        popup.show();
		    });
		    
	        return false;
		});
	}).get();
	
	AJS.bind("show.dialog", function(e, data) {
	    console.log("Dialog shown " + data.dialog.id);	    
	});
	
	AJS.bind("hide.dialog", function(e, data) {
	    console.log("Dialog hidden " + data.dialog.id);	    
	});
	
	AJS.bind("removed.dialog", function(e, data) {
	    console.log("Dialog removed " + data.dialog.id);	    
	});

});