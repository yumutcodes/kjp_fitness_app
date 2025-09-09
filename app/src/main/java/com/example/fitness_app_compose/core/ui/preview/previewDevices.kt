package com.example.fitness_app_compose.core.ui.preview

import androidx.compose.ui.tooling.preview.Preview

class DevicesGroups {
    companion object {
        const val Phone = "Telefonlar"
        const val Tablet = "Tabletler"
        const val Foldable = "Katlanabilir Cihazlar"
    }
}
@Preview(
    name = "Telefon (Normal)",
    device = "id:pixel_6",
    group = DevicesGroups.Phone,
    showBackground = true
)
@Preview(
    name = "Küçük Telefon (Genişlik: 360dp, Uzunluk: 640dp, Oran: 16:9)",
    group = DevicesGroups.Phone,
    widthDp = 360,
    heightDp = 640,
    showBackground = true
)
@Preview(
    name = "Büyük Telefon (Genişlik: 412dp, Uzunluk: 915dp, Oran: 915:412)",
    group = DevicesGroups.Phone,
    widthDp = 412,
    heightDp = 915,
    showBackground = true
)

@Preview(
    name = "Katlanabilir (Açık)",
    device = "id:pixel_fold",
    group = DevicesGroups.Foldable,
    showBackground = true
)
@Preview(
    name = "Tablet",
    device = "id:pixel_tablet",
    group = DevicesGroups.Tablet,
    showBackground = true
)
annotation class DevicePreviews{

}