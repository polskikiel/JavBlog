function articleTextReturn(text, page) {        // for articles -> article.jsp
    var flag = false;
    var start = false;
    var htmlText = "";

    for (var i = page * 3000; i < text.length; i++) {
        if (text.charAt(i) == '.' || text.charAt(i) == '!' || text.charAt(i) == '?') {
            if (!flag) {
                flag = true;
            }
        }
        else {
            if (flag) {
                htmlText += "<br/>";
                flag = false;

                if(!start) {
                    start = true;
                }

                if (i > 3000 * page + 3000) {
                    break;
                }
            }
        }

        if (start)
            htmlText += text.charAt(i);
    }
    var x = document.createElement("INPUT");
    x.setAttribute("type", "textarea");
    x.setAttribute("value", htmlText);
    x.setAttribute("path", "body");
    document.getElementById("containerOp").appendChild(x);
}