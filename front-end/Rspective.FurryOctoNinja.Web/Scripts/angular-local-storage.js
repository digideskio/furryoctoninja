﻿/**
 * An Angular module that gives you access to the browsers local storage
 * @version v0.2.0 - 2015-05-10
 * @link https://github.com/grevory/angular-local-storage
 * @author grevory <greg@gregpike.ca>
 * @license MIT License, http://www.opensource.org/licenses/MIT
 */!function (a, b, c) { "use strict"; function d(a) { return /^-?\d+\.?\d*$/.test(a.replace(/["']/g, "")) } var e = b.isDefined, f = b.isUndefined, g = b.isNumber, h = b.isObject, i = b.isArray, j = b.extend, k = b.toJson, l = b.module("LocalStorageModule", []); l.provider("localStorageService", function () { this.prefix = "ls", this.storageType = "localStorage", this.cookie = { expiry: 30, path: "/" }, this.notify = { setItem: !0, removeItem: !1 }, this.setPrefix = function (a) { return this.prefix = a, this }, this.setStorageType = function (a) { return this.storageType = a, this }, this.setStorageCookie = function (a, b) { return this.cookie.expiry = a, this.cookie.path = b, this }, this.setStorageCookieDomain = function (a) { return this.cookie.domain = a, this }, this.setNotify = function (a, b) { return this.notify = { setItem: a, removeItem: b }, this }, this.$get = ["$rootScope", "$window", "$document", "$parse", function (a, b, c, l) { function m(a, b) { return "true" === b || "false" === b ? "true" === b : b } var n, o = this, p = o.prefix, q = o.cookie, r = o.notify, s = o.storageType; c ? c[0] && (c = c[0]) : c = document, "." !== p.substr(-1) && (p = p ? p + "." : ""); var t = function (a) { return p + a }, u = function () { try { var c = s in b && null !== b[s], d = t("__" + Math.round(1e7 * Math.random())); return c && (n = b[s], n.setItem(d, ""), n.removeItem(d)), c } catch (e) { return s = "cookie", a.$broadcast("LocalStorageModule.notification.error", e.message), !1 } }(), v = function (b, c) { if (f(c) ? c = null : (h(c) || i(c) || g(+c || c)) && (c = k(c)), !u || "cookie" === o.storageType) return u || a.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), r.setItem && a.$broadcast("LocalStorageModule.notification.setitem", { key: b, newvalue: c, storageType: "cookie" }), B(b, c); try { n && n.setItem(t(b), c), r.setItem && a.$broadcast("LocalStorageModule.notification.setitem", { key: b, newvalue: c, storageType: o.storageType }) } catch (d) { return a.$broadcast("LocalStorageModule.notification.error", d.message), B(b, c) } return !0 }, w = function (b) { if (!u || "cookie" === o.storageType) return u || a.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), C(b); var c = n ? n.getItem(t(b)) : null; return c && "null" !== c ? "{" === c.charAt(0) || "[" === c.charAt(0) || d(c) ? JSON.parse(c, m) : c : null }, x = function () { var b, c; for (b = 0; b < arguments.length; b++) if (c = arguments[b], u && "cookie" !== o.storageType) try { n.removeItem(t(c)), r.removeItem && a.$broadcast("LocalStorageModule.notification.removeitem", { key: c, storageType: o.storageType }) } catch (d) { a.$broadcast("LocalStorageModule.notification.error", d.message), D(c) } else u || a.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), r.removeItem && a.$broadcast("LocalStorageModule.notification.removeitem", { key: c, storageType: "cookie" }), D(c) }, y = function () { if (!u) return a.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), !1; var b = p.length, c = []; for (var d in n) if (d.substr(0, b) === p) try { c.push(d.substr(b)) } catch (e) { return a.$broadcast("LocalStorageModule.notification.error", e.Description), [] } return c }, z = function (b) { var c = p ? new RegExp("^" + p) : new RegExp, d = b ? new RegExp(b) : new RegExp; if (!u || "cookie" === o.storageType) return u || a.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), E(); var e = p.length; for (var f in n) if (c.test(f) && d.test(f.substr(e))) try { x(f.substr(e)) } catch (g) { return a.$broadcast("LocalStorageModule.notification.error", g.message), E() } return !0 }, A = function () { try { return b.navigator.cookieEnabled || "cookie" in c && (c.cookie.length > 0 || (c.cookie = "test").indexOf.call(c.cookie, "test") > -1) } catch (d) { return a.$broadcast("LocalStorageModule.notification.error", d.message), !1 } }(), B = function (b, d, e) { if (f(d)) return !1; if ((i(d) || h(d)) && (d = k(d)), !A) return a.$broadcast("LocalStorageModule.notification.error", "COOKIES_NOT_SUPPORTED"), !1; try { var j = "", l = new Date, m = ""; if (null === d ? (l.setTime(l.getTime() + -864e5), j = "; expires=" + l.toGMTString(), d = "") : g(e) && 0 !== e ? (l.setTime(l.getTime() + 24 * e * 60 * 60 * 1e3), j = "; expires=" + l.toGMTString()) : 0 !== q.expiry && (l.setTime(l.getTime() + 24 * q.expiry * 60 * 60 * 1e3), j = "; expires=" + l.toGMTString()), b) { var n = "; path=" + q.path; q.domain && (m = "; domain=" + q.domain), c.cookie = t(b) + "=" + encodeURIComponent(d) + j + n + m } } catch (o) { return a.$broadcast("LocalStorageModule.notification.error", o.message), !1 } return !0 }, C = function (b) { if (!A) return a.$broadcast("LocalStorageModule.notification.error", "COOKIES_NOT_SUPPORTED"), !1; for (var d = c.cookie && c.cookie.split(";") || [], e = 0; e < d.length; e++) { for (var f = d[e]; " " === f.charAt(0) ;) f = f.substring(1, f.length); if (0 === f.indexOf(t(b) + "=")) { var g = decodeURIComponent(f.substring(p.length + b.length + 1, f.length)); try { return JSON.parse(g, m) } catch (h) { return g } } } return null }, D = function (a) { B(a, null) }, E = function () { for (var a = null, b = p.length, d = c.cookie.split(";"), e = 0; e < d.length; e++) { for (a = d[e]; " " === a.charAt(0) ;) a = a.substring(1, a.length); var f = a.substring(b, a.indexOf("=")); D(f) } }, F = function () { return s }, G = function (a, b, c, d) { d = d || b; var f = w(d); return null === f && e(c) ? f = c : h(f) && h(c) && (f = j(c, f)), l(b).assign(a, f), a.$watch(b, function (a) { v(d, a) }, h(a[b])) }, H = function () { for (var a = 0, c = b[s], d = 0; d < c.length; d++) 0 === c.key(d).indexOf(p) && a++; return a }; return { isSupported: u, getStorageType: F, set: v, add: v, get: w, keys: y, remove: x, clearAll: z, bind: G, deriveKey: t, length: H, cookie: { isSupported: A, set: B, add: B, get: C, remove: D, clearAll: E } } }] }) }(window, window.angular);