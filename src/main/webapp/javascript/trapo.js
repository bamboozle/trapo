function $(elementId) {
	return document.getElementById(elementId);
}

function areyousure(message) {
	if (confirm(message)) {
		return history.back(-1);
	}
}

function askedit(form_id, url) {
	var form = $(form_id);
	form.action = url;
	form.submit();
}

function askdeletion(form_id, message, url) {
	if(confirm(message)) {
		var form = $(form_id);
		form.action = url;
		form.submit();
	}
}