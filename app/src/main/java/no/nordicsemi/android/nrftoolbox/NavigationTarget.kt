package no.nordicsemi.android.nrftoolbox

data class NavigationTarget(val destination: NavDestination, val args: String? = null) {

    val url: String = args?.let { "$destination?$args" } ?: destination.id
}
