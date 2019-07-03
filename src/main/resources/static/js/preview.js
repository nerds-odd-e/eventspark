$(function() {
    var $nextBtn = $('#next');
    var $prevBtn = $('#prev');

    $nextBtn.on('click', changePreviewPage);
    $prevBtn.on('click', changePreviewPage);
})

function changePreviewPage(e) {
    var index = $(e.target).attr('data-index');
    var $previewForm = $('#form');

    $previewForm
        .attr('action', '/preview/' + index)
        .attr('method', 'post')
        .submit();
}