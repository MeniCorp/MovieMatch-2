# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep data classes for Gson serialization
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.menicorp.moviematch.data.** { *; }
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }