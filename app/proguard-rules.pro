# Ultimate Mods ProGuard Rules

-keepattributes Signature, *Annotation*, EnclosingMethod

# Protect critical classes (anti-mod protection)
-keep class com.ultimatemods.core.** { *; }
-keep class com.ultimatemods.network.** { *; }
-keep class com.ultimatemods.security.** { *; }

# Hide everything else (rename to a, b, c...)
-renamesourceclassattribute SourceFile

# Make API reflection harder
-keepattributes !LineNumber, !SourceFile

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
