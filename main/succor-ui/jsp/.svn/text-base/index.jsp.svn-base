<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.rosettastone.succor.web.sso.SuccUserDetails" %>
<%@ page import="java.util.ResourceBundle" %>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Succor</title>
<link type="text/css" href="styles/smoothness/jquery-ui-1.7.3.custom.css" rel="Stylesheet"/>
<link type="text/css" href="styles/jqModal.css" rel="stylesheet">
<link type="text/css" href="styles/styles.css" rel="stylesheet">
<script type="text/javascript" src="AC_OETags.js" language="javascript"></script>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jqModal.js" language="javascript"></script>

<!--[if gte IE 5.5]>
<![if lt IE 7]>
<style type="text/css">
* html .jqmWindow {
    position: absolute;
    top: expression((document.documentElement.scrollTop || document.body.scrollTop) + Math.round(17 * (document.documentElement.offsetHeight || document.body.clientHeight) / 100) + 'px');
}
</style>
<![endif]>
<![endif]-->


<script language="JavaScript" type="text/javascript">


    <!--
    // -----------------------------------------------------------------------------
    // Globals
    // Major version of Flash required
    var requiredMajorVersion = 9;
    // Minor version of Flash required
    var requiredMinorVersion = 0;
    // Minor version of Flash required
    var requiredRevision = 124;
    // -----------------------------------------------------------------------------
    // -->
    <%
//    String baseUrl = "${baseurl}";
    String user = "anonymous";
    if(SecurityContextHolder.getContext().getAuthentication()!=null){
        user =((SuccUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName();
    }
    ResourceBundle resource = ResourceBundle.getBundle("application");
    String baseUrl=resource.getString("baseurl");
    %>


    var baseUrl = "<%=baseUrl%>";
    var user = "<%=user%>";
    function openPreview() {
        // let's run through all possible values: 90%, nothing or a value in pixel
        var newWidth = 0, newHeight = 0, newLeft = 0, newTop = 0;
        newHeight = Math.floor(parseInt($(window).height()) * 0.8);
        newTop = Math.floor(parseInt($(window).height() - newHeight) / 2);
        newWidth = Math.floor(parseInt($(window).width()) * 0.9);
        newLeft = Math.floor(parseInt($(window).width() / 2) - parseInt(newWidth) / 2);


        $('#modalWindow').css({
            width: newWidth,
            height: newHeight,
            opacity: 0
        }).jqmShow().animate({
                                 width: newWidth,
                                 height: newHeight,
                                 top: newTop,
                                 left: newLeft,
                                 marginLeft: 0,
                                 opacity: 1
                             }, 'slow');
    }

    $(document).ready(function() {
        var closeModal = function(hash) {
            var modalWindow = $(hash.w);
            var modalContainer = $('iframe', modalWindow);
            modalContainer.html('').attr('src', '');
            modalWindow.fadeOut('2000', function() {
                hash.o.remove();
            });
        };
        var openInFrame = function(hash) {
            var modalWindow = $(hash.w);
            var modalContainer = $('iframe', modalWindow);
            var myUrl = baseUrl + "/services/preview";
            modalContainer.html('').attr('src', myUrl);
        };

        $('#modalWindow').jqm({
            overlay: 70,
            modal: true,
            trigger: 'a.thickbox',
            target: '#jqmContent',
            onHide: closeModal,
            onShow: openInFrame
        });

    });
</script>
</head>

<body scroll="no">

<%--<table width="100%"><tr>--%>
    <%--<td width="100%" align="right" nowrap>Wellcome, <%=((SuccUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName()%></td>--%>
    <%--<td nowrap><a href="j_spring_cas_security_logout">Sign Out</a></td>--%>
    <%--<td nowrap><a href="">Live</a></td>--%>
    <%--<td nowrap><a href="">Help</a></td></tr>--%>
<%--</table>--%>

<div id="modalWindow" class="jqmWindow" style="display:none;">
    <div id="jqmTitle">
        <button class="jqmClose">
            X
        </button>
        <span id="jqmTitleText"><div>Template preview</div></span>
    </div>
    <iframe id="jqmContent" src="">
    </iframe>
</div>

<script language="JavaScript" type="text/javascript">
<!--
// Version check based upon the values entered above in "Globals"
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if (hasRequestedVersion) {
		AC_FL_RunContent(
					"src", "succor.swf",
					"width", "100%",
					"height", "100%",
					"align", "middle",
					"id", "Succor",
					"quality", "high",
					"bgcolor", "#C0C0C0",
                    "wmode", "opaque",
					"name", "Succor",
                    "FlashVars", "baseUrl="+baseUrl+"&user="+user,
					"allowScriptAccess","sameDomain",
					"type", "application/x-shockwave-flash",
					"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="Succor" width="400" height="300"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="succor.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#C0C0C0" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="succor.swf" quality="high" bgcolor="#C0C0C0"
				width="100%" height="100%" name="Succor" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
</body>
</html>
