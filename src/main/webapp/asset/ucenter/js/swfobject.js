/* SWFObject v2.2 <http://code.google.com/p/swfobject/> 
	是根据MIT许可证<http://www.opensource.org/licenses/mit-license.php>发布的 
*/
var swfobject = function（）{var D =“undefined”，r =“object”，S =“Shockwave Flash”，W =“ShockwaveFlash.ShockwaveFlash”，q =“application / x-shockwave-flash” SWFObjectExprInst“，x =”onreadystatechange“，O = window，j = document，t = navigator，T = false，U = [h]，o = []，N = E，B，J = false，a = false，n，G，m = true，M = function（）{var aa = typeof j.getElementById！= D && typeof j.getElementsByTagName！= D && typeof j.createElement！= D，ah = t.userAgent.toLowerCase（），Y = t.platform.toLowerCase（），ae = Y？/win/.test（Y）：/ win / .test（ah），ac = Y？/ mac / （Y）：/ mac / .test（ah），af = / webkit / .test（ah）？parseFloat（ah.replace（/^.* webkit \ /（\ d +（\。\ d +）？ $ /，“$ 1”））：false，X =！+“\ v1”，ag = [0,0,0]，ab = null; if（typeof t.plugins！= D && typeof t.plugins [S] = r）{ab = t.plugins [S] .description; if（ab &&！（typeof t.mimeTypes！= D && t.mimeTypes [q] &&！t.mimeTypes [q] .enabledPlugin））{T = true; X = false; ab = ab.replace（/^.* \ s +（\ S + \ s + \ S + $） ，“$ 1”）; ag [0] = parseInt（ab.replace（/^(.*)\..*$/,"$ 1“），10）; ag [1] = parseInt（ab.replace ^）*。*。（。*）\ s。* $ /，“$ 1”），10）; ag [2] = / [a-zA-Z] / test（ab）？parseInt（ab.replace /^.*[a-zA-Z]+(.*)$/,"$1"),10):0}}else{if(typeof O.ActiveXObject！= D）{try {var ad = new ActiveXObject （W）; if（ad）{ab = ad.GetVariable（“$ version”）; if（ab）{X = true; ab = ab.split（“”）[1] .split（“，”）; ag = [parseInt（ab [0]，10），parseInt（ab [1]，10），parseInt（ab [2]，10）]}}} pv：ag，wk：af，ie：X，win：ae，mac：ac}}（），k = function（）{if（！M.w3）{return} if（（typeof j.readyState！= D && j .readyState ==“complete”）||（typeof j.readyState == D &&（j.getElementsByTagName（“body”）[0] || j.body）））{f（j.addEventListener！= D）{j.addEventListener（“DOMContentLoaded”，f，false）} if（M.ie && M.win）{j.attachEvent（x，function（）{if（j。 ready（J）{return} try {j.getState ==“complete”）{j.detachEvent（x，arguments.callee）; f（）}}）; if documentElement.doScroll（“left”）} catch（X）{setTimeout（arguments.callee，0）; return} f（） {return} if（！/ loaded | complete / .test（j.readyState））{setTimeout（arguments.callee，0）; return} f（） （Z）} catch（aa）（）{if（J）{return} try {var Z = j.getElementsByTagName（“body”）[0] .appendChild（C（“span”））; Z.parentNode.removeChild {return} J = true; var X = U.length; for（var Y = 0; Y <X; Y ++）{U [Y] } else {U [U.length] = X}} function s（Y）{if（typeof O.addEventListener！= D）{O.addEventListener（“load”，Y，false）} else {if（typeof j.addEventListener！= D）{j.addEventListener（“load”，Y，false）} else {if（typeof O.attachEvent！= D）{i Y）} else {if（typeof O.onload ==“function”）{var X = O.onload; O.onload = function（）{X（）; Y（）}} else {O.onload = Y}函数v（）{var X = j.getElementsByTagName（“body”）[0]; var aa = C（ r）; aa.setAttribute（“type”，q）; var Z = X.appendChild（aa）; if（Z）{var Y = 0;（function（）{if（typeof Z.GetVariable！ var ab = Z.GetVariable（“$ version”）; if（ab）{ab = ab.split（“”）[1] .split（“，”）; M.pv = [parseInt（ab [0]， 10），parseInt（ab [1]，10），parseInt（ab [2]，10）]} else {if（Y <10）{Y ++; setTimeout（arguments.callee，10）; return}}。 remove（aa）; Z = null; H（）}）else {H（）}} function H（）{var ag = o.length; if（ag> 0）{for（var af = 0; af <ag; af ++）{var Y = o [af] .id; var ab = o [af] .callbackFn;var aa = {成功：false，id：Y}; if（M.pv [0]> 0）{var ae = c（Y）; if（ae）{if（F（o [af] .swfVersion）&&如果（a，a，b，a，a，b，a，a，b，a，b， [af] .expressInstall && A（））{var ai = {}; ai.data = o [af] .expressInstall; ai.width = ae.getAttribute（“width”）||“0”; ai.height = ae。 getAttribute（“height”）||“0”; if（ae.getAttribute（“class”））{ai.styleclass = ae.getAttribute（“class”）} if（ae.getAttribute（“align”））{ai 。var = a.getAlementsByTagName（“param”）; var ac = X.length; for（var ad = 0; ad <ac; ad ++） {if（X [ad] .getAttribute（“name”）。toLowerCase（）！=“movie”）{ah [X [ad] .getAttribute（“name”）] = X [ad] .getAttribute（“value” ）}} P（ai，ah，Y，ab）} else {p（ae）;if（ab）{ab（aa）}}}}} else {w（Y，true）; if（ab）{var Z = z（Y）; if（Z && typeof Z.SetVariable！= D）{aa.success = true; aa.ref = Z} ab（aa）}}}}} function z（aa）{var X = null; var Y = c（aa）; if（Y && Y.nodeName ==“OBJECT”）{if （type of Y.SetVariable！= D）{X = Y} else {var Z = Y.getElementsByTagName（r）[0]; if（Z）{X = Z}}} return X} function A（）{return！函数P（aa，ab，X，Z）{a = true; E = Z（a，b，a， || null; B = {success：false，id：X}; var ae = c（X）; if（ae）{if（ae.nodeName ==“OBJECT”）{l = g（ae）; Q = null} else {l = ae; Q = X} aa.id = R; if（typeof aa.width == D ||（！/％$ /。test（aa.width）&& parseInt（aa.width，10） <a> </ b> </ a> <a> <a> <a> <a> <a> aa.height =“137”} j.title = j.title.slice（0,47）+“ -  Flash Player Installation“; var ad = M.ie && M.win？”ActiveX“：”PlugIn“，ac =”MMredirectURL =“+ O.location.toString（）。replace（/＆/ g，”％26“） +“＆MMplayerType =”+ ad +“＆MMdoctitle =”+ j.title; if（typeof ab.flashvars！= D）{ab.flashvars + =“＆”+ ac} else {ab.flashvars = ac} if（M.ie && M .win && ae.readyState！= 4）{var Y = C（“div”）; X + =“SWFObjectNew”; Y.setAttribute（“id”，X）; ae.parentNode.insertBefore（Y，ae）; ae.style .display =“none”;（function（）{if（ae.readyState == 4）{ae.parentNode.removeChild（ae）} else {setTimeout（arguments.callee，10）}}） （X，Y）; X（X，Y）; X，Y，X，Y） parentNode.replaceChild（g（Y），X）; Y.style.display =“none”;（function（）{if（Y.readyState == 4）{Y.parentNode.removeChild（Y）} else {setTimeout（arguments.callee，10）}}）（）} else {Y.parentNode.replaceChild Y），Y）}} function g（ab）{var aa = C（“div”）; if（M.win && M.ie）{aa.innerHTML = ab.innerHTML} else {var Y = ab.getElementsByTagName ）[0]; if（Y）{var ad = Y.childNodes; if（ad）{var X = ad.length; for（var Z = 0; Z <X; Z ++） ] .nodeType == 1 && ad [Z] .nodeName ==“PARAM”）&&！（ad [Z] .nodeType == 8））{aa.appendChild（ad [Z] .cloneNode（true））}}}} }返回aa}函数u（ai，ag，Y）{var X，aa = c（Y）; if（M.wk && M.wk <312）{return X} if（aa）{if（typeof ai.id = = d）{ai.id = Y} if（M.ie && M.win）{var ah =“”; for（var ae in ai）{if（ai [ae]！= Object.prototype [ae]）{if （ae.toLowerCase（）==“data”）{ag.movi​​e = ai [ae]} else {if（ae.toLowerCase（）==“styleclass”）{ah + ='class =“'+ ai [ae] +'“'} else {if（ae.toLowerCase（）！=“classid”）{ah + =“”+ ae +'=“'+ ai [ae] +'”'}}}}} var af =“”; for ad in ag）{if（ag [ad]！= Object.prototype [ad]）{af + ='<param name =“'+ ad +'”value =“'+ ag [ad] +'”/>' } aa.outerHTML ='<object classid =“clsid：D27CDB6E-AE6D-11cf-96B8-444553540000”'+ ah +“>”+ af +“</ object>”; N [N.length] = ai.id; X = c（ai.id）} else {var Z = C（r）; Z.setAttribute（“type”，q）; for（var ac in ai）{if（ai [ac]！= Object.prototype [ac ] {if（ac.toLowerCase（）==“classid”）{Z.setAttribute（“class”，ai [ac] （ac，ai [ac]）}}}} for（var ab in ag）{if（ag [ab]！= Object.prototype [ab] && ab.toLowerCase ab，ag [ab]）}} aa.parentNode。replaceAll（“name”，X）; aChange（Z，aa）; X = Z}} return X} function e（Z，X，Y）{var aa = C（“param”）; aa.setAttribute（“name”，X）; aa.setAttribute （X && X.nodeName ==“OBJECT”）{if（M.ie && M.win）{X .style.display =“none”;（function（）{if（X.readyState == 4）{b（Y）} else {setTimeout（arguments.callee，10）}}） .removeChild（X）}}} function b（Z）{var Y = c（Z）; if（Y）{for（var X in Y）{if（typeof Y [X] ==“function”）{Y [X] = null}} Y.parentNode.removeChild（Y）}} function c（Z）{var X = null; try {X = j.getElementById（Z）} catch（Y）{} return X} function C （Z，X，Y）函数F（Z，X，Y）{Z.attachEvent ）{var Y = M.pv，X = Z.split（“。”）; X [0] = parseInt（X [0]，10）; X [1] = parseInt（X [1]，10） | 0; X [2] = parseInt（X [2]，10）|| 0; return（Y [0]X [0]＆X [0] && Y [1] == X [1] X [0] ||（Y [0] == X [0] && Y [1] ＆; Y [2]> = X [2]））？true：false} function v（ac，Y，ad，ab）{if（M.ie && M.mac）{return} var aa = j.getElementsByTagName ）[0]; if（！aa）{return} var X =（ad && typeof ad ==“string”）？ad：“screen”; if（ab）{n = null; G = null} if | Z！setAttribute（“media”，X）; n = aa.appendChild（Z）; G！= X）{var Z = ）; if（M.ie && M.Win）; if（M.ie && M.Win）;如果（M.ie && M.Win）是一个对象， {n.appendChild（j.createTextNode（ac +“{”+ Y +“））{if（n && typeof n.addRule == r）{n.addRule（ac，Y）}} else {if（n && typeof j.createTextNode！= D） }“}）}}}}}}} function {}“隐藏”; if（J && c（Z））{c（Z）.style.visibility = Y} else {v（“＃”+ Z，“visibility：”+ Y） = / [\\\“<> \;] /; var X = Z.exec（Y）！= null; return X && typeof encodeURIComponent！= D？encodeURIComponent（Y）：Y} var d = function （Mie && M.win）{window.attachEvent（“onunload”，function（）{var ac = I.length; for（var ab = 0; ab <ac; ab ++）{I [ab] [0] .detachEvent （var aa = 0; aa <Z; aa ++）{y（N [aa]）}对于（var（a [a] Y在M）{M [Y] = null} M = null; for（var X in swfobject）{swfobject [X] = null} swfobject = null}）}}（）; return {registerObject：function ，aa，Z）{if（M.w3 && ab && X）{var Y = {}; Y.id = ab; Y.swfVersion = X; Y.expressInstall = aa; Y.callbackFn = Z; o [o.length] （x，y）; w（ab，false）} else {if（Z）{Z（{success：false，id：ab}）}}} getObjectById：function（X）{if（M.w3）{return z ）}}，embedSWF：function（ab，ah，ae，ag，Y，aa，Z，ad，af，ac）{var X = {success：false，id：ah}; if（M.w3 &&！（M.wk && M.wk <312）&& ab && ah && ae && ag && Y）{w（ah，false） ; K（function（）{ae + =“”; ag + =“”; var aj = {}; if（af && typeof af === r）{for（var al in af）{aj [al] = af [al] }} aj.data = ab; aj.width = ae; aj.height = ag; var am = {}; if（ad && typeof ad === r）{for（var ak in ad）{am [ak] = ad [ak]}} if（Z && typeof Z === r）{for（var ai in Z）{if（typeof am.flashvars！= D）{am.flashvars + =“＆”+ ai +“=”+ Z [ai ]} else {am.flashvars = ai +“=”+ Z [ai]}}} if（F（Y））{var an = u（aj，am，ah）; if（aj.id == ah）{ w（ah，true）} X.success = true; X.ref = an} else {if（aa && A（））{aj.data = aa; P（aj，am，ah，ac）; return} else {w （a，b，c，c，c，c，d，d，d，d） getFlashPlayerVersion：function（）{return {major：M.pv [0]，minor：M。pv [1]，release：M.pv [2]}}，hasFlashPlayerVersion：F，createSWF：function（Z，Y，X）{if（M.w3）{return u（Z，Y，X）} else {返回undefined}}，showExpressInstall：function（Z，aa，X，Y）{if（M.w3 && A（））{P（Z，aa，X，Y）}}，removeSWF：function addLoadEvent：K，addLoadEvent：{v（aa，Z，Y，X）}} s，getQueryParamValue：function（aa）{var Z = j.location.search || j.location.hash; if（Z）{if（/ \\\ test（Z））{Z = Z.split ？}）[1]} if（aa == null）{return L（Z）} var Y = Z.split（“＆”）; for（var X = 0; X <Y.length; X ++）{if （Y [X] .indexOf（“=”））== aa（Y [X] .substring（0，Y [X] .indexOf 1）））}}} return“”}，expressInstallCallback：function（）{if（a）{var X = c（R）; if（X && l）{X.parentNode.replaceChild（l，X）; if ）{w（Q，true）; if（M.ie && M.win）{l.style.display =“block”}} if（E）{E（B）}} a = false}}}}（）;