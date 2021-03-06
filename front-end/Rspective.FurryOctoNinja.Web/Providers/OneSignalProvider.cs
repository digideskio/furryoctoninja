﻿using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.Web.Providers
{
    public class OneSignalProvider
    {
        private const string AppId = "4e20a7ca-fd86-11e4-b8d4-83591acb8ccc";
        private const string ApiKey = "NGUyMGE4NGMtZmQ4Ni0xMWU0LWI4ZDUtZTdhZDJjZTgxZWVk";
        private static readonly string NotificationUri = "https://onesignal.com/api/v1/notifications";

        public async static Task<bool> NotifyMobileDevices(string eventType, string title, string content)
        {
            var payload = new Dictionary<string, object>() {
                { "app_id", AppId },
                { "contents", new Dictionary<string, string>() {
                        { "en", content }
                    }
                },
                { "heading", new Dictionary<string, string>() {
                        { "en", title }
                    }
                },
                { "tags", new Dictionary<string, string>[] {
                        new Dictionary<string, string>() {
                            { "key", "app" },
                            { "relation", "=" },
                            { "value", "furryoctoninja" }
                        }
                    }
                },
                { "data", new Dictionary<string, object>() {
                        { "eventType", eventType }
                    }
                },
                { "isAndroid", true },
                { "isIos", true }
            };

            var json = JsonConvert.SerializeObject(payload);

            using (var httpClient = PrepareClient()) 
            {
                var httpResponse = await httpClient.PostAsJsonAsync<Dictionary<string, object>>(NotificationUri, payload);
            }

            return true;
        }

        private static HttpClient PrepareClient()
        {
            var client = new HttpClient();

            client.DefaultRequestHeaders.Authorization = 
                new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", ApiKey);

            return client;
        }
    }
}

/*
 * if (!applicationId || !deviceId || !notification) {
            console.error("[OneSignal] Error - (sendPush) Missing parameters Application: %s Device: %s Notification: %j",
                applicationId, deviceId, notification);
            deferred.reject({});
            return deferred.promise;
        }

        if (!config.onesignal.enabled) {
            console.error("[OneSignal] Error - (sendPush) OneSignal Disabled Application: %s Device: %s Notification: %j",
                applicationId, deviceId, notification);
            deferred.reject({});
            return deferred.promise;
        }

        var _notification = {
            app_id      : applicationId,
            contents    : { en : notification.body },
            heading     : { en : notification.title },
            tags        : [{ key: "DeviceId", relation: "=", value: deviceId }],
            data        : notification,
            isAndroid   : true
        };

        request.post(getUrl("notifications"), _.merge({ json: _notification }, options), function(error, response) {
            if (error) {
                console.error("[OneSignal] Error - (sendPush) Push not sent Application: %s Device: %s Error: %s Stack: %j Notification: %j",
                    applicationId, deviceId, error.message, error.stack, notification);
                return deferred.reject(error);
            }

            console.log("[OneSignal] (sendPush) Notification sent Application: %s Device: %s Response: %j Notification: %j",
                applicationId, deviceId, response.body, notification);
            deferred.resolve(response.body);
        });
*/
