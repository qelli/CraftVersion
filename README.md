<div align="center">
  <h1>GitCraft</h1>
</div>

Have you ever thought about using Git to manage your server's configuration in a reliable and scalable way? **GitCraft** is the solution.

> **This is a beta plugin.**  
> Its primary purpose is to give server administrators better control over configuration files, enabling faster rollbacks and full configuration versioning.

This plugin assumes a basic understanding of Git, both for configuration and ongoing server management.

## Setup

Before getting started, you must configure the following required options: 

- `git.exec-path`
~ `git.working-dib`

> The plugin will not function unless these are properly set.

Once configured, Git will be available directly through your Minecraft server’s console. You can start by initializing a repository:

```bash
git init
```

This creates a Git repository at the path defined in `git.working-dib`.

> _*IMPORTANT:*_   
> You ***must*** create a `.gitignore` file within `git.working-dir`. If this file is missing, the plugin will throw an error on startup.

From here, you can add a remote repository and continue with your standard Git workflow:

```bash
git remote add origin ssh@github.com/myRepo.git`
```

## TODOs

- Add user-friendly commands under the `/gitcraft` command suite.