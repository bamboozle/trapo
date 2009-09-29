function $(elementId) {
	return document.getElementById(elementId);
}

function areyousure(message) {
	if (confirm(message)) {
		return history.back(-1);
	}
}

function askedit(form_id, action) {
	var form = $(form_id);
	form.action = action;
	form.submit();
}

function askdeletion(form_id, message, action) {
	if(confirm(message)) {
		var form = $(form_id);
		form.action = action;
		form.submit();
	}
}