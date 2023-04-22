package hu.bme.aut.android.receptes.ui.editor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val editorRepository: EditorRepository
) : ViewModel() {}