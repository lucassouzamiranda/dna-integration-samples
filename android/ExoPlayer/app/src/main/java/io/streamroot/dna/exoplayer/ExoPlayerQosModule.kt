package io.streamroot.dna.exoplayer

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.Player.STATE_IDLE
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import io.streamroot.dna.core.PlaybackState
import io.streamroot.dna.core.QosModule

class ExoPlayerQosModule(
    exoPlayer: SimpleExoPlayer
) : QosModule(), Player.EventListener {

    init {
        exoPlayer.addListener(this)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}

    override fun onSeekProcessed() {
        playbackStateChange(PlaybackState.SEEKING)
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        trackSwitchOccurred()
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        playbackErrorOccurred()
    }

    override fun onLoadingChanged(isLoading: Boolean) {}

    override fun onPositionDiscontinuity(reason: Int) {}

    override fun onRepeatModeChanged(repeatMode: Int) {}

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            STATE_IDLE -> {
                playbackStateChange(PlaybackState.IDLE)
            }
            STATE_BUFFERING -> {
                playbackStateChange(PlaybackState.BUFFERING)
            }
            STATE_READY -> {
                playbackStateChange(if (playWhenReady) PlaybackState.PLAYING else PlaybackState.PAUSING)
            }
            STATE_ENDED -> {
                playbackStateChange(PlaybackState.ENDED)
            }
        }
    }
}
