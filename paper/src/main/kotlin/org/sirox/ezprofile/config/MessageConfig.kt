package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class MessageConfig : OkaeriConfig() {

    @Comment("Missing permissions message")
    @Comment("<permissions> - placeholder for permissions")
    var missingPermissions: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Missing permissions: <permissions>"

    @Comment("","Player not found message")
    @Comment("<player> - placeholder for player")
    var playerNotFound: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Player <player> not found"

    @Comment("","Invalid usage message")
    var invalidUsageMessage: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Invalid usage"

    @Comment("","Reload error message")
    var reloadErrorMessage: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] An error occurred while trying to reload the configuration"

    @Comment("","Reload message")
    var reloadMessage: String = "<#AAAAAA>[<#5DF083>✔<#AAAAAA>] Plugin has been reloaded"

    @Comment("","Wrong action message")
    var wrongActionMessage: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Wrong action"

    @Comment("","Not profile owner error message")
    var notProfileOwner: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] You are not owner of that profile"
}