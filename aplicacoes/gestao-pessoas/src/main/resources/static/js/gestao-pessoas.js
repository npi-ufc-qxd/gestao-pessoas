function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find("i.fa")
        .toggleClass('fa-caret-right fa-chevron-down');
}
$('.panel-body').on('hidden.bs.collapse', toggleChevron);
$('.panel-body').on('shown.bs.collapse', toggleChevron);

$(".gp-btn-presenca").on("click", function() {
	swal("Bom trabalho!", "Presença realizada!", "success");
});

$('.data').datepicker();
