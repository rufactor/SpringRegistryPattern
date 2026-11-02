# Spring Registry Pattern

A small Spring Boot project that demonstrates the **Registry Pattern** — a design pattern for dynamically dispatching work to the correct handler at runtime, without `if-else` or `switch` chains.

## What is the Registry Pattern?

The Registry Pattern is a way to organize extensible, pluggable components. Instead of hard-coding which handler processes which request, you **register** each handler under a unique key (code). At runtime, the registry looks up the key and delegates to the matching handler.

This is especially useful when:
- New handlers can be added without modifying existing code (Open/Closed Principle).
- The set of handlers is not known at compile time or is configured externally.
- You want to avoid long `switch`/`if-else` chains that grow with every new case.

## How This Implementation Works

The project uses a **self-registration** approach built on three Spring mechanisms:

### 1. Custom Annotation — `@RegistryCode`

```java
@RegistryCode("1")
public class GreetingEmailGenerator implements EmailGenerator { ... }
```

Each generator implementation declares its unique lookup key via the `@RegistryCode` annotation.

### 2. Interface with Default Self-Registration — `EmailGenerator`

The `EmailGenerator` interface provides a `default` method `registerMyself()` that:
- Reads the `@RegistryCode` value from the implementing class via reflection.
- Calls `EmailGeneratorService.register(code, this)` to add itself to the registry map.

Spring invokes this method through **setter injection** (`@Autowired`) on every bean that implements the interface.

### 3. Central Registry — `EmailGeneratorService`

The service holds a `Map<String, EmailGenerator>` and exposes:
- `register(String code, EmailGenerator)` — adds a generator to the map.
- `generateTemplate(EmailTemplate)` — looks up the generator by `templateCode` and delegates to it.

### Registration Flow

```
Spring creates GreetingEmailGenerator bean
       ↓
@Autowired triggers registerMyself() on the interface
       ↓
getRegistryCode() reads @RegistryCode("1")
       ↓
EmailGeneratorService.register("1", GreetingEmailGenerator)
       ↓
Map now contains: "1" → GreetingEmailGenerator
```

## Adding a New Generator

To add a new email type, you only need to create one class:

```java
@Component
@RegistryCode("3")
public class WelcomeEmailGenerator implements EmailGenerator {

    @Override
    public String generate(EmailTemplate emailTemplate) {
        return emailTemplate.getSubject() + "\n Welcome aboard!";
    }
}
```

No changes to `EmailGeneratorService`, no new `if-else` branches — the generator registers itself automatically.

## Strengths of This Approach

| Aspect | Benefit |
|--------|---------|
| **Open/Closed Principle** | Add new generators without touching existing code. |
| **No conditional logic** | The service never checks `if code == "1"` — lookup is data-driven. |
| **Self-registration** | Implementors register themselves via the interface default method — no manual wiring. |
| **Spring-native** | Uses `@Component`, `@Autowired`, and default interface methods — no custom infrastructure. |
| **Thread-safe** | Uses `ConcurrentHashMap` for safe concurrent reads. |
| **Duplicate detection** | `putIfAbsent()` prevents silent overwrites when two beans share the same code. |

### 4. Unit Tests
Comprehensive unit tests cover:
- Registry registration of multiple generators.
- Template generation for each registered generator.
- Missing template code throws `UnsupportedOperationException` with the correct code in the message.
- Duplicate registry codes throw `IllegalStateException`.
- Empty registry handling.
- Unmodifiable registry collection exposure.

## Running the Project

```bash
./gradlew bootRun
```

The `EmailTemplateProducer` runs every 3 seconds and generates two sample emails (greeting and birthday), printing the results to stdout.

## Tech Stack

- **Java 25**
- **Spring Boot 4.1.0**
- **Lombok** (for boilerplate reduction in `EmailTemplate`)
- **Gradle** (build tool)

## Setting Up SSH Key for GitHub

To push changes to GitHub without being prompted for credentials, set up SSH key authentication.

### 1. Generate an SSH Key Pair

```bash
ssh-keygen -t ed25519 -C "your-email@example.com"
```

- Press Enter to accept the default file location (`~/.ssh/id_ed25519`).
- Press Enter twice to skip the passphrase (recommended for agent/automation use).

> **Note:** If you get `Saving key "~/.ssh/id_ed25519" failed: No such file or directory`, create the directory first:
> ```bash
> mkdir -p ~/.ssh
> chmod 700 ~/.ssh
> ```

This creates two files in `~/.ssh/`:
- **`id_ed25519`** — the private key (keep it secret).
- **`id_ed25519.pub`** — the public key (add this to GitHub).

### 2. Add the Public Key to GitHub

1. Copy the public key:
   ```bash
   cat ~/.ssh/id_ed25519.pub
   ```
   Or on macOS:
   ```bash
   pbcopy < ~/.ssh/id_ed25519.pub
   ```

2. Go to GitHub → **Settings** → **SSH and GPG keys** → **New SSH key**.

3. Paste the public key, give it a title (e.g., "Junie Agent"), and click **Add key**.

### 3. Configure Git to Use SSH

If the repository is currently using HTTPS, switch it to SSH:

```bash
git remote set-url origin git@github.com:<username>/<repo>.git
```

### 4. Test the Connection

```bash
ssh -T git@github.com
```

You should see: *"Hi username! You've successfully authenticated, but GitHub does not provide shell access."*

### 5. For Agent/Automated Use

Load the key into the SSH agent so no passphrase prompt appears:

```bash
ssh-add ~/.ssh/id_ed25519
```

On macOS, persist the key across reboots using the keychain:

```bash
ssh-add --apple-use-keychain ~/.ssh/id_ed25519
```

### Quick Reference

| Step | Command |
|------|---------|
| Create `.ssh` folder | `mkdir -p ~/.ssh && chmod 700 ~/.ssh` |
| Generate key | `ssh-keygen -t ed25519 -C "your-email@example.com"` |
| Copy public key | `cat ~/.ssh/id_ed25519.pub` |
| Add to GitHub | Settings → SSH and GPG keys → New SSH key |
| Switch to SSH | `git remote set-url origin git@github.com:user/repo.git` |
| Test connection | `ssh -T git@github.com` |
| Load key in agent | `ssh-add ~/.ssh/id_ed25519` |
