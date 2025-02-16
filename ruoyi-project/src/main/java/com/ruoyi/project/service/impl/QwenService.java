package com.ruoyi.project.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class QwenService {

    private static final String QWEN_API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
    private static final String QWEN_API_KEY = "key"; // 替换为你的 API 密钥

    /**
     * 调用通义千问 API 生成任务描述
     *
     * @param taskName 任务名称
     * @return 生成的任务描述
     * @throws Exception 如果调用失败或解析响应出错
     */
    public static String callQwen(String taskName) throws Exception {
        // 创建请求体
        RequestBody requestBody = new RequestBody(
                "qwen-plus", // 使用的模型名称
                new Message[]{
                        new Message("system", "You are a helpful assistant."),
                        new Message("user", "为任务名称 '" + taskName + "' 生成任务描述；200字即可")
                }
        );

        // 将请求体转换为 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(requestBody);

        // 创建 URL 对象
        URL url = new URL(QWEN_API_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            // 设置请求方法为 POST
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            // 设置 API 密钥
            String auth = "Bearer " + QWEN_API_KEY;
            httpURLConnection.setRequestProperty("Authorization", auth);

            // 启用输入输出流
            httpURLConnection.setDoOutput(true);

            // 写入请求体
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Unexpected code: " + responseCode);
            }

            // 读取响应体
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            System.out.println(response.toString());
            JSONObject jsonObject = JSONObject.parseObject(response.toString());
            // 获取choices数组
            JSONObject choices = jsonObject.getJSONArray("choices").getJSONObject(0);

            // 获取message对象
            JSONObject message = choices.getJSONObject("message");

            // 获取content内容
            String content = message.getString("content");

            // 解析响应体
//            ResponseBody responseBody = objectMapper.readValue(response.toString(), ResponseBody.class);
//            return responseBody.choices[0].message.getContent().trim();
            return content;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    /**
     * 请求体类
     */
    static class RequestBody {
        private String model;
        private Message[] messages;

        public RequestBody(String model, Message[] messages) {
            this.model = model;
            this.messages = messages;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Message[] getMessages() {
            return messages;
        }

        public void setMessages(Message[] messages) {
            this.messages = messages;
        }
    }

    /**
     * 消息类
     */
    static class Message {
        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;

        public Message() {
        }

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * 响应体类
     */
    static class ResponseBody {
        @JsonProperty("choices")
        private Choice[] choices;

        public ResponseBody() {
        }

        public Choice[] getChoices() {
            return choices;
        }

        public void setChoices(Choice[] choices) {
            this.choices = choices;
        }
    }

    /**
     * Choice 类
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Choice {
        @JsonProperty("message")
        private Message message;

        public Choice() {
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
}