package jed.cmp.azurepusher

import com.azure.data.model.Document

class ImageDocument(id: String? = null): Document(id) {
    var imageUrl = ""
}
