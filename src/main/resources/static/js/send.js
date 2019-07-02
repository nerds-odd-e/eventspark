$(function() {
    var $form = $('#form');
    var $previewBtn = $form.find('#preview');
    var $sendBtn = $form.find('#send');

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

})