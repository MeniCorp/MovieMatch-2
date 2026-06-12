# MovieMatch 2

Eine Android App für Filmvorschläge basierend auf TMDB API.

## Build

```bash
./gradlew assembleDebug   # Debug APK
./gradlew assembleRelease  # Release APK
```

APK outputs: `app/build/outputs/apk/{debug,release}/`

## GitHub Release Workflow

A GitHub Actions workflow at `.github/workflows/release.yml` handles building and publishing releases.

### Trigger

- **Manual**: Go to GitHub Actions → "Build & Release" → "Run workflow"

### Inputs

| Input         | Description                      | Default |
|---------------|----------------------------------|---------|
| version_name  | Semantic version (e.g. 1.0.0)    | 1.0.0   |
| version_code  | Integer version code              | 1       |
| release_notes | Custom release notes (optional)   | —       |

### What it does

1. Checks out code
2. Sets up JDK 17 + Gradle
3. Builds debug APK (`assembleDebug`)
4. Builds release APK (`assembleRelease`)
5. Creates a GitHub Release with tag `v{version_name}`
6. Uploads both APKs as release assets

### Future

Once we have signing keys configured in GitHub Secrets, the release build will produce signed APKs/AABs instead of unsigned APKs. For now, debug builds are fully functional for testing.