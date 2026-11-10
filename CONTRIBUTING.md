# Contributing

Use the issue tracker for bugs and proposals. For bugs, include the GregBlock Edition version, Forge version, steps to reproduce, and relevant logs. Remove personal information and credentials from logs before attaching them.

1. Fork the repository and create a branch from `main`.
2. Use JDK 17 and run `./gradlew reobfJar` (Windows: `.\gradlew.bat reobfJar`).
3. Test gameplay changes in a fresh Minecraft 1.20.1 Forge world. For compatibility changes, list the relevant mod versions and what you verified.
4. Open a pull request targeting `main`. Link any related issue and credit the original commits when backporting.

Contributions are accepted under the repository's [LGPL-3.0 license](LICENSE). See [AI_POLICY.md](AI_POLICY.md) for disclosure and review requirements.
