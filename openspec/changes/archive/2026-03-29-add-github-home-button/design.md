## Context

项目为单 Activity + Navigation Component 模板：`MainActivity` 承载 `NavHost`，起始目的地为 `FirstFragment`（首屏即「主页」）。当前首屏已有 `button_first` 用于跳转到 `SecondFragment`。`AndroidManifest` 尚未声明 `INTERNET` 权限。

## Goals / Non-Goals

**Goals:**

- 在首屏（`FirstFragment`）提供明确用于「打开我的 GitHub 主页」的跳转入口。
- 新页面在应用内用 WebView 加载可配置的 GitHub URL（默认占位，便于用户改为自己的 `https://github.com/<username>`）。
- 通过 Navigation 进入新 Fragment，系统返回键/导航返回可回到首屏。

**Non-Goals:**

- 不实现 GitHub OAuth、私有仓库或 API 集成。
- 不要求离线缓存或复杂错误重试策略（可做基础加载失败提示）。

## Decisions

1. **首屏入口位置**  
   在 `FirstFragment` 增加独立按钮（或与现有「Next」并存的新按钮），文案如「查看 GitHub」，避免与现有 `button_first` 语义混淆。  
   **理由**：用户要求「主页」跳转；当前起始页即 `FirstFragment`，改动面最小。

2. **展示方式：WebView**  
   使用 `WebView` + `WebViewClient` 在应用内展示 GitHub 页面。  
   **理由**：完整呈现 GitHub 个人页，无需外部浏览器依赖用户偏好；与「显示我的 GitHub 页面」一致。  
   **备选**：`Custom Tabs` — 体验接近浏览器，但偏离「应用内页面」的常见理解，故不作为首选。

3. **URL 配置**  
   将目标 URL 放在 `res/values/strings.xml`（如 `github_profile_url`），便于用户替换为自己的主页。  
   **理由**：无后端时最简单；若未来需多环境可再抽取。

4. **导航**  
   新增 `GitHubProfileFragment`（或等价命名），在 `nav_graph.xml` 中从 `FirstFragment` 声明 `action` 指向该目的地；Fragment 内启用 `WebSettings` JavaScript（GitHub 页面需要）。

5. **权限与网络安全**  
   在 `AndroidManifest` 添加 `android.permission.INTERNET`。默认仅 HTTPS；若需明文 HTTP 再考虑 `networkSecurityConfig`（本设计不强制）。

## Risks / Trade-offs

- **[Risk] GitHub 页面在 WebView 中可能被站点策略或登录态影响显示** → **Mitigation**：规格上验收「加载用户配置的 HTTPS GitHub URL」；复杂登录不在范围内。  
- **[Risk] WebView 内存与进程杀死** → **Mitigation**：常规 Fragment 生命周期内销毁 WebView 或 `onDestroyView` 清理。  
- **[Risk] 用户未替换占位 URL** → **Mitigation**：字符串资源注释说明需改为自己的主页链接。

## Migration Plan

- 首次发布该功能：安装即包含新权限与导航；无数据迁移。  
- 回滚：移除 Fragment、导航边与权限即可恢复先前行为。

## Open Questions

- 无（占位 URL 默认值可在实现时定为 `https://github.com` 或文档式占位，由 tasks 细化）。
