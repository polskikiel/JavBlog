function htmlText(text) {
    var flag = false;
    var htmlText = "";

    for (var i = 0; i < text.length; i++) {
        if (text.charAt(i) == '.' || text.charAt(i) == '!' || text.charAt(i) == '?') {
            if (!flag) {
                flag = true;
            }
        }
        else {
            if (flag) {
                htmlText += "<br/>";
                flag = false;
            }
        }

        htmlText += text.charAt(i);
    }

    document.write(htmlText);
}

function articleText(text, page) {        // for articles -> article.jsp
    var flag = false;
    var start = false;
    var htmlText = "";

    if(page == 0) {
        start = true;
    }
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

    document.write(htmlText);
}
