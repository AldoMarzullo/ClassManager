<link rel="stylesheet" href="resources/lib/bootstrap-table/xeditable/dist/css/bootstrap-editable.css">
<script src="resources/lib/bootstrap-table/xeditable/dist/js/bootstrap-editable.min.js"></script>
<script src="resources/lib/bootstrap-table/xeditable/dist/js/address.js"></script>

<script src="resources/lib/bootpag/jquery.bootpag.min.js"></script>


<!-- table to excel -->
<script type="text/javascript" src="/resources/lib/js-xlsx/dist/xlsx.core.min.js"></script>
<script type="text/javascript" src="resources/lib/FileSaver/FileSaver.min.js"></script>
<script type="text/javascript" src="resources/lib/FileSaver/Blob.js"></script>
<script type="text/javascript" src="/resources/lib/TableExportlib/dist/js/tableexport.min.js"></script>

<script src="resources/script/scoresPage.js"></script>

<script type="text/javascript" src="resources/lib/tablesorter/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="resources/lib/tablesorter/tablesorter.css" />
<script>
$(document).ready(function($) 
{ 
	$('.tablesorter').tablesorter({
	    // *** Appearance ***
	    // fix the column widths
	    widthFixed : true,
	    dateFormat : "mmddyyyy",

	    // *** Functionality ***
	    // starting sort direction "asc" or "desc"
	    sortInitialOrder : "asc",
	    // These are detected by default,
	    // but you can change or disable them
	    headers : {
	        // set "sorter : false" (no quotes) to disable the column
	        0: { sorter: "text" },
	    },
	    // extract text from the table - this is how is
	    // it done by default
	    textExtraction : {
	        0: function(node) { return $(node).text(); },
	        1: function(node) { return $(node).text(); }
	    },
	    // forces the user to have this/these column(s) sorted first
	    sortForce : null,
	    // initial sort order of the columns
	    // [[columnIndex, sortDirection], ... ]
	    sortList : [],
	    // default sort that is added to the end of the users sort
	    // selection.
	    sortAppend : null,
	    // Use built-in javascript sort - may be faster, but does not
	    // sort alphanumerically
	    sortLocaleCompare : false,
	    // Setting this option to true will allow you to click on the
	    // table header a third time to reset the sort direction.
	    sortReset: false,
	    // Setting this option to true will start the sort with the
	    // sortInitialOrder when clicking on a previously unsorted column.
	    sortRestart: false,
	    // The key used to select more than one column for multi-column
	    // sorting.
	    sortMultiSortKey : "shiftKey",

	    // *** Customize header ***
	    onRenderHeader  : function() {
	        // the span wrapper is added by default
	        $(this).find('span').addClass('headerSpan');
	    },
	    // jQuery selectors used to find the header cells.
	    selectorHeaders : 'thead th.sortable',

	    // *** css classes to use ***
	    cssAsc        : "headerSortUp",
	    cssChildRow   : "expand-child",
	    cssDesc       : "headerSortDown",
	    cssHeader     : "header",
	    tableClass    : 'tablesorter',

	    // *** prevent text selection in header ***
	    cancelSelection : true,

	    // *** send messages to console ***
	    debug : false
	});
}); 
</script>