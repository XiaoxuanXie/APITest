## Context

`GitHubProfileFragment` 使用 `WebView` 加载 `https://github.com/...` 等地址。用户反馈在 WebView 内出现 **连接超时**，表现为长时间无内容或系统/站点级超时提示，影响「查看 GitHub 主页」这一核心路径。

当前实现仅设置 `javaScriptEnabled` 与默认 `WebViewClient()`，未处理 `onReceivedError` / HTTP 错误，也未在 UI 上区分加载中与失败，排障与体验均不足。

## Goals / Non-Goals

**Goals:**

- 在常见网络环境下，提高 GitHub HTTPS 页在 WebView 中的可加载性（合理配置 WebSettings、兼容现代站点行为）。
- 当加载失败或超时时，**明确提示**并允许 **重试**，避免无声失败。
- 加载过程中提供 **可见反馈**（如进度条或占位），减少「假死」感。

**Non-Goals:**

- 不保证在受限网络（如目标站点不可达、企业防火墙）下一定能打开 GitHub；此类情况以清晰错误提示为主。
- 不引入第三方浏览器内核或替换为 Custom Tabs（除非后续单独变更决定）。

## Decisions

1. **WebSettings 与兼容性**  
   启用 `domStorageEnabled`（与 `javaScriptEnabled` 一起满足多数现代站点）；按需设置 `mixedContentMode`（如 `MIXED_CONTENT_COMPATIBILITY_MODE`）以适配 HTTPS 页中的混合资源；缓存策略使用默认或 `LOAD_DEFAULT` 避免异常缓存导致坏状态。  
   **理由**：GitHub 等站点依赖 DOM 存储与脚本；错误配置易导致白屏或长时间挂起。

2. **User-Agent**  
   将 WebView 的 User-Agent 设为与常见移动 Chrome 接近的字符串（避免过旧 WebView 默认 UA 被站点拒绝或走慢路径）。  
   **理由**：部分站点对默认 Android WebView UA 处理不佳，可能表现为超时或空白页。  
   **备选**：保持系统默认 UA — 若验证无差异可回退以减小维护面。

3. **错误与重试**  
   扩展 `WebViewClient`：在 `onPageStarted` / `onPageFinished` 中驱动加载 UI；在 `onReceivedError`（及适用的 API 级别上的 `onReceivedHttpError`）中标记失败并展示错误文案与「重试」操作。  
   **理由**：超时与网络错误需显式回调才能与默认「一直转圈」区分。

4. **UI 形态**  
   在 `fragment_github_profile` 上采用 **FrameLayout 叠加**：底层 `WebView`，上层 `ProgressBar`/`ProgressIndicator` 与错误/重试容器（`TextView` + `Button`），避免替换整个 Fragment 结构。  
   **理由**：改动面小、易测试。

5. **清单与网络安全**  
   已具备 `INTERNET`。若仅 HTTPS 且无自定义证书需求，**不强制**新增 `networkSecurityConfig`；若后续发现 cleartext 子资源被拦，再补配置。  
   **理由**：先解决 WebView 与客户端回调，再处理证书/明文等边缘情况。

## Risks / Trade-offs

- **[Risk] 用户网络环境仍无法访问 GitHub** → **Mitigation**：错误提示中说明可能为网络或站点限制；重试仍可用。  
- **[Risk] 自定义 UA 与站点政策变化** → **Mitigation**：集中常量或资源，便于调整。  
- **[Risk] 多窗口/进程 WebView** → **Mitigation**：保持单 Fragment 内单 WebView，生命周期内 `destroy` 已有。

## Migration Plan

- 发布即生效；无数据迁移。  
- 回滚：还原 `GitHubProfileFragment` 与布局即可。

## Open Questions

- 无（若真机仍超时，可再记录日志与 `shouldInterceptRequest` 诊断，属后续迭代）。
