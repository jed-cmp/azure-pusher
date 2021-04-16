package jed.cmp.azurepusher

import com.azure.data.model.Document

class ImageDocument(id: String? = null): Document(id) {
    var price = 12.45
    var level = "high"
}
