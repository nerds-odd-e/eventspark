const $ = require('jquery')

$(() => {
    var $form = $('#form');
    var $previewBtn = $form.find('#preview');
    var $sendBtn = $form.find('#send');
    var $loadTemplateBtn = $form.find('#load-template');

    $previewBtn.on('click', () => {
        $form
            .attr('action', '/preview/0')
            .submit();
    });

    $sendBtn.on('click', () => {
          $form
              .attr('action', '/send')
              .submit();
      });

    $loadTemplateBtn.on('click', () => {
        $form
            .attr('action', '/load')
            .submit();
    });
})