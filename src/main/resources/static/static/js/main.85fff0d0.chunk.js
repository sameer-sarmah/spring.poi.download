(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{15:function(t,e,i){t.exports=i.p+"static/media/logo.5d5d9eef.svg"},19:function(t,e,i){t.exports=i(48)},25:function(t,e,i){},28:function(t,e,i){},48:function(t,e,i){"use strict";i.r(e);var r=i(3),n=i.n(r),a=i(12),s=i.n(a),h=(i(25),i(0)),c=i(2),u=i(17),v=i(13),o=i(18),f=i(5),p=i.n(f),l=i(14),d=i(15),y=i.n(d),m=(i(28),function(){function t(e,i){Object(h.a)(this,t),this.baseURL="/",this.httpSvc=e,this.serviceName=-1!==i.indexOf("http")?i:this.baseURL+i}return Object(c.a)(t,[{key:"findById",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if("object"===typeof e){var r=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("GET",r,e,i)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var n=this.serviceName+"/"+t+"?"+e;return this.httpSvc.initiateRequest("GET",n,{},i)}var a=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("GET",a,{},i)}},{key:"findAll",value:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};if("object"===typeof t){var i=this.serviceName;return this.httpSvc.initiateRequest("GET",i,t,e)}if(t&&"string"===typeof t){t=this.validateQueryParamString(t);var r=this.serviceName+"?"+t;return this.httpSvc.initiateRequest("GET",r,{},e)}var n=this.serviceName;return this.httpSvc.initiateRequest("GET",n,{},e)}},{key:"update",value:function(t,e,i){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{};if("object"===typeof i){var n=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("PUT",n,i,r,e)}if(i&&"string"===typeof i){i=this.validateQueryParamString(i);var a=this.serviceName+"/"+t+"?"+i;return this.httpSvc.initiateRequest("PUT",a,{},r,e)}var s=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("PUT",s,{},r,e)}},{key:"patch",value:function(t,e,i){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{};if("object"===typeof i){var n=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("PATCH",n,i,r,e)}if(i&&"string"===typeof i){i=this.validateQueryParamString(i);var a=this.serviceName+"/"+t+"?"+i;return this.httpSvc.initiateRequest("PATCH",a,{},r,e)}var s=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("PATCH",s,{},r,e)}},{key:"create",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if("object"===typeof e){var r=this.serviceName;return this.httpSvc.initiateRequest("POST",r,e,i,t)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var n=this.serviceName+"?"+e;return this.httpSvc.initiateRequest("POST",n,{},i,t)}var a=this.serviceName;return this.httpSvc.initiateRequest("POST",a,{},i,t)}},{key:"delete",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if("object"===typeof e){var r=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("DELETE",r,e,i)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var n=this.serviceName+"/"+t+"?"+e;return this.httpSvc.initiateRequest("DELETE",n,{},i)}var a=this.serviceName+"/"+t;return this.httpSvc.initiateRequest("DELETE",a,{},i)}},{key:"validateQueryParamString",value:function(t){if((t=t.trim()).indexOf("?")>-1){var e=t.split("?");if(2===e.length)return e[1];throw Error("Invalid query param string")}return t}}]),t}()),g=function(){function t(e,i,r){Object(h.a)(this,t),this.baseURL="/",this.httpSvc=e,this.serviceName=-1!==i.indexOf("http")?i:this.baseURL+i,this.entityPath=r}return Object(c.a)(t,[{key:"findById",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},r=this.getEntityIdAsString(t);if("object"===typeof e){var n=this.serviceName+"/"+this.entityPath+"("+r+")";return this.httpSvc.initiateRequest("GET",n,e,i)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var a=this.serviceName+"/"+this.entityPath+"("+r+")?"+e;return this.httpSvc.initiateRequest("GET",a,{},i)}var s=this.serviceName+"/"+this.entityPath+"("+r+")";return this.httpSvc.initiateRequest("GET",s,{},i)}},{key:"findAll",value:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};if("object"===typeof t){var i=this.serviceName+"/"+this.entityPath;return this.httpSvc.initiateRequest("GET",i,t,e)}if(t&&"string"===typeof t){t=this.validateQueryParamString(t);var r=this.serviceName+"/"+this.entityPath+"?"+t;return this.httpSvc.initiateRequest("GET",r,{},e)}var n=this.serviceName+"/"+this.entityPath;return this.httpSvc.initiateRequest("GET",n,{},e)}},{key:"find",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};return"object"===typeof e?this.httpSvc.initiateRequest("GET",t,e,i):e&&"string"===typeof e?(e=this.validateQueryParamString(e),this.httpSvc.initiateRequest("GET",t,{},i)):this.httpSvc.initiateRequest("GET",t,{},i)}},{key:"update",value:function(t,e,i){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{},n=this.getEntityIdAsString(t);if("object"===typeof i){var a=this.serviceName+"/"+this.entityPath+"("+n+")";return this.httpSvc.initiateRequest("PUT",a,i,r,e)}if(i&&"string"===typeof i){i=this.validateQueryParamString(i);var s=this.serviceName+"/"+this.entityPath+"("+n+")?"+i;return this.httpSvc.initiateRequest("PUT",s,{},r,e)}var h=this.serviceName+"/"+this.entityPath+"("+n+")";return this.httpSvc.initiateRequest("PUT",h,{},r,e)}},{key:"patch",value:function(t,e,i){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{},n=this.getEntityIdAsString(t);if("object"===typeof i){var a=this.serviceName+"/"+this.entityPath+"("+n+")";return this.httpSvc.initiateRequest("PATCH",a,i,r,e)}if(i&&"string"===typeof i){i=this.validateQueryParamString(i);var s=this.serviceName+"/"+this.entityPath+"("+n+")?"+i;return this.httpSvc.initiateRequest("PATCH",s,{},r,e)}var h=this.serviceName+"/"+this.entityPath+"("+n+")";return this.httpSvc.initiateRequest("PATCH",h,{},r,e)}},{key:"create",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if("object"===typeof e){var r=this.serviceName+"/"+this.entityPath;return this.httpSvc.initiateRequest("POST",r,e,i,t)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var n=this.serviceName+"/"+this.entityPath+"?"+e;return this.httpSvc.initiateRequest("POST",n,{},i,t)}var a=this.serviceName+"/"+this.entityPath;return this.httpSvc.initiateRequest("POST",a,{},i,t)}},{key:"delete",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},r=this.getEntityIdAsString(t);if("object"===typeof e){var n=this.serviceName+"/"+this.entityPath+"("+r+")";return this.httpSvc.initiateRequest("DELETE",n,e,i)}if(e&&"string"===typeof e){e=this.validateQueryParamString(e);var a=this.serviceName+"/"+this.entityPath+"("+r+")?"+e;return this.httpSvc.initiateRequest("DELETE",a,{},i)}var s=this.serviceName+"/"+this.entityPath+"("+r+")";return this.httpSvc.initiateRequest("DELETE",s,{},i)}},{key:"getEntityIdAsString",value:function(t){var e="";if("string"===typeof t)return"'".concat(t,"'");if("object"===typeof t){for(var i in t)if(t.hasOwnProperty(i)){var r=t[i];"number"===typeof r?e+=i+"="+r+",":"string"===typeof r&&(e+=i+"='"+r+"',")}e=e.substr(0,e.length-1)}return e}},{key:"validateQueryParamString",value:function(t){if((t=t.trim()).indexOf("?")>-1){var e=t.split("?");if(2===e.length)return e[1];throw Error("Invalid query param string")}return t}}]),t}(),S=i(16),P=i.n(S),E=function t(){Object(h.a)(this,t),this.promise=new Promise(function(t,e){this.resolve=t,this.reject=e}.bind(this)),this.then=this.promise.then.bind(this.promise),this.catchError=this.promise.catch.bind(this.promise)},T=function(){function t(e,i,r){Object(h.a)(this,t),this.status=e,this.textStatus=i,this.errorThrown=r}return Object(c.a)(t,[{key:"getTextStatus",value:function(){return this.textStatus}},{key:"getError",value:function(){return this.errorThrown}},{key:"getStatusCode",value:function(){return this.status}}]),t}(),R=function(){function t(){Object(h.a)(this,t)}return Object(c.a)(t,[{key:"initiateRequest",value:function(t,e){var i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{},n=arguments.length>4&&void 0!==arguments[4]?arguments[4]:null,a=arguments.length>5?arguments[5]:void 0;t=t.toLowerCase();var s={};s.url=e,s.method=t,s.withCredentials=!0,"object"===typeof i&&(s.params=i),s.headers=r,"POST"!==t&&"PUT"!==t||(s.data=n),a&&(s.responseType=a);var h=P.a.request(s),c=new E;return h.then(function(t){c.resolve(t.data)}).catch(function(t){var e=t.status,i=t.statusText,r=t.data,n=new T(e,i,r);c.reject(n)}),c}}]),t}(),b=function(){function t(){Object(h.a)(this,t),this.httpSvc=new R}return Object(c.a)(t,[{key:"getRESTModel",value:function(t){return new m(this.httpSvc,t)}},{key:"getODataModel",value:function(t,e){return new g(this.httpSvc,t,e)}},{key:"validateServiceNameOrEntityName",value:function(t){if((t=t.trim()).indexOf("/")>-1){var e=t.split("/");if(2===e.length)return e[1];throw Error("Invalid service or entity name")}return t}}]),t}(),q=function(){var t=Object(l.a)(p.a.mark(function t(e,i){var r,n;return p.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return r=(new b).getRESTModel(e),n={},t.prev=2,t.next=5,r.findAll(n,i);case 5:t.next=10;break;case 7:t.prev=7,t.t0=t.catch(2),console.log(t.t0);case 10:case"end":return t.stop()}},t,null,[[2,7]])}));return function(e,i){return t.apply(this,arguments)}}(),N=function(t){function e(){return Object(h.a)(this,e),Object(u.a)(this,Object(v.a)(e).apply(this,arguments))}return Object(o.a)(e,t),Object(c.a)(e,[{key:"render",value:function(){return n.a.createElement("div",{className:"App"},n.a.createElement("header",{className:"App-header"},n.a.createElement("img",{src:y.a,className:"App-logo",alt:"logo"}),n.a.createElement("p",{onClick:function(){q("downloadtext",{Accept:"text/plain"})}},"download text"),n.a.createElement("p",{onClick:function(){q("downloadpdf",{Accept:"application/pdf"})}},"download pdf"),n.a.createElement("a",{target:"_parent",href:"http://localhost:8080/downloadtext",download:!0}," download text"),n.a.createElement("a",{target:"_parent",href:"http://localhost:8080/downloadpdf",download:!0}," download pdf")))}}]),e}(r.Component);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));s.a.render(n.a.createElement(N,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(t){t.unregister()})}},[[19,1,2]]]);
//# sourceMappingURL=main.85fff0d0.chunk.js.map