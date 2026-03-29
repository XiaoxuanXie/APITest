## Why

当前应用主页缺少快速访问开发者 GitHub 主页的入口；用户希望在主界面一键打开并查看自己的 GitHub 页面，便于展示或自检个人主页。

## What Changes

- 在应用主界面（首页/首屏）增加一个可点击的跳转按钮。
- 新增一个用于展示 GitHub 页面的界面（建议使用 WebView 加载指定 URL，便于显示完整 GitHub 个人页）。
- GitHub 页面 URL 应可配置或易于修改（例如资源字符串或常量），默认指向占位或示例地址，由用户替换为自己的主页链接。
- 通过 Navigation 或等价方式从主页进入该页面，并支持返回。

## Capabilities

### New Capabilities

- `github-profile-view`: 定义从主页进入、在应用内展示指定 GitHub URL 页面及返回行为的验收要求。

### Modified Capabilities

- （无；`openspec/specs/` 下尚无既有能力规格。）

## Impact

- **Android 应用模块**：`MainActivity`、导航图、首屏 Fragment 布局、新增 Fragment/Activity（视实现而定）、`AndroidManifest.xml`（若新增 Activity）。
- **依赖**：可能增加 WebView 相关权限（若需 `INTERNET`）与网络安全配置。
- **无**：对外 API 或后端变更。
