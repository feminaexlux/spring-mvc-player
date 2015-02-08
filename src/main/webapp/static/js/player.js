function Player() {
    this.play = function (checksum) {
        if (checksum == null || checksum == undefined) {
            return;
        }

        $.get("/music/" + checksum, function (data) {
            $('#song_player').html(data);
        });
    }
}

$(function () {
    var player = new Player();

    $(".song-link").on('click', function () {
        var checksum = $(this).attr('data-song');
        player.play(checksum);
    });
});