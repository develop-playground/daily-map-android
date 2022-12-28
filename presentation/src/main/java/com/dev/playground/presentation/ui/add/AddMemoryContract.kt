package com.dev.playground.presentation.ui.add

import androidx.annotation.StringRes
import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState
import java.io.File

class AddMemoryContract {

    data class State(
        val addMemoryState: AddMemoryState,
        val isLoading: Boolean = false,
    ) : UiState

    sealed interface AddMemoryState {
        object Empty : AddMemoryState
        data class SelectedPhoto(
            val fileList: List<File>,
            val information: Information,
        ) : AddMemoryState {

            data class Information(
                val location: Memory.Location,
                val address: String,
                val createdDate: String,
            ) {
                val metadata
                    get() = "$createdDate - $address"
            }
        }

        val isEmpty
            get() = this is Empty

        val formatAddress
            get() = (this as? SelectedPhoto)?.information?.metadata.orEmpty()
    }

    sealed interface Event : UiEvent {
        data class OnClickRemovePhoto(val index: Int) : Event
        object OnClickDrop : Event
    }

    sealed interface Effect : UiEffect {
        object Dropped : Effect
        sealed class ShowToast(@StringRes val message: Int) : Effect {
            object FailUpload : ShowToast(R.string.add_memory_fail_upload)
            object NotSelectPhoto : ShowToast(R.string.add_memory_not_select_photo)
            object EmptyLocation : ShowToast(R.string.add_memory_missing_locate_information)
        }
    }

}