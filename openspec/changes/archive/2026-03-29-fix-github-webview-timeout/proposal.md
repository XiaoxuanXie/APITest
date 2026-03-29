## Why

应用内 GitHub 个人页 WebView 出现「连接超时」，用户无法正常查看页面。需要在不破坏现有导航与入口的前提下，消除或显著缓解该问题，并在网络不可达时给出可理解的反馈。

## What Changes

- 排查并修正导致 WebView 长时间无响应或超时的配置与实现（例如 WebSettings、混合内容、缓存策略、错误回调缺失等）。
- 为页面加载增加可见状态（加载中）与失败路径（错误提示 + 重试），避免白屏或仅显示系统级超时。
- 在合理范围内验证：在可用网络下可完成 HTTPS 页面加载；若环境限制（如目标站点不可达）则至少明确提示而非静默失败。

## Capabilities

### New Capabilities

- （无）

### Modified Capabilities

- `github-profile-view`: 更新「加载 GitHub 页面」相关需求，补充加载失败/超时时的行为；可增加与 WebView 可靠性相关的验收场景。

## Impact

- **代码**：`GitHubProfileFragment`、相关布局（若增加错误/重试 UI）、可选 `network_security_config` 或其它清单级配置。
- **规格**：主规格 `openspec/specs/github-profile-view/spec.md` 将在归档同步时合并 delta。
- **无**：对外 API 变更。
