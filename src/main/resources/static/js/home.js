$(function() {
    var $form = $('#form');
    var $previewBtn = $form.find('#preview');
    var $sendBtn = $form.find('#send');
    var $loadTemplateBtn = $form.find('#load-template');

    $previewBtn.on('click', function() {
        $form
            .attr('action', '/preview')
            .submit();
    });

    $sendBtn.on('click', function() {
          $form
              .attr('action', '/send')
              .submit();
      });

    $loadTemplateBtn.on('click', function() {
        $form
            .attr('action', '/load')
            .submit();
    });
})