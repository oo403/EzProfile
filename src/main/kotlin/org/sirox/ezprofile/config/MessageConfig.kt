package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class MessageConfig : OkaeriConfig() {

    @Comment("Missing permissions message")
    @Comment("<permissions> - placeholder for permissions")
    var missingPermissions: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Missing permissions: <permissions>"

    @Comment("Invalid usage message")
    var invalidUsageMessage: String = "<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Invalid usage"
}