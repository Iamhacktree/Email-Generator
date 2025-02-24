package com.Project.email_generator.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Project.email_generator.model.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailGeneratorService {
	
	private final WebClient webClient;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
	
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	
	public String generateEmailReply(EmailRequest emailRequest) {
		//build a prompt
		String prompt = buildPrompt(emailRequest);
		
		//craft a request
		Map<String, Object> requestBody = Map.of(
				"contents", new Object[] {
						Map.of("parts", new Object[] {
								Map.of("text", prompt)
						})
				}
		);
		
//		Do request and get response
//		this is how to send request
//			{
//			  "contents": [{
//			    "parts":[{"text": "Explain how AI works"}]
//			    }]
//			   }
		String response = webClient.post()
				.uri(geminiApiUrl + geminiApiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		//Return the response
		return extractResponseContent(response);
	}
	
//		this is the response we get from GEMINI
//	{
//	    "candidates": [
//	        {
//	            "content": {
//	                "parts": [
//	                    {
//	                        "text": "AI, or Artificial Intelligence, doesn't work in a single, unified way.  Instead, it encompasses a broad range of techniques and approaches aimed at creating systems that can perform tasks that typically require human intelligence.  Here's a breakdown of some key concepts:\n\n**1. Data is King:** AI systems, especially modern ones, rely heavily on vast amounts of data.  This data is used to train the algorithms, enabling them to learn patterns and make predictions. The quality and quantity of data significantly impact the performance of the AI.\n\n**2. Algorithms are the Tools:** Algorithms are sets of rules and statistical techniques that AI systems use to process data.  Different types of AI use different algorithms:\n\n* **Machine Learning (ML):** This is a subset of AI where systems learn from data without explicit programming. Instead of being explicitly programmed with rules, they identify patterns and relationships in data to make predictions or decisions.  Examples include:\n    * **Supervised Learning:** The algorithm is trained on labeled data (data where the desired output is known).  The algorithm learns to map inputs to outputs. Examples include image classification (identifying cats vs. dogs) and spam detection.\n    * **Unsupervised Learning:** The algorithm is trained on unlabeled data. It tries to find structure and patterns in the data without explicit guidance. Examples include clustering (grouping similar data points) and dimensionality reduction.\n    * **Reinforcement Learning:** The algorithm learns through trial and error by interacting with an environment. It receives rewards for desirable actions and penalties for undesirable actions, learning to optimize its behavior over time.  Examples include game playing (e.g., AlphaGo) and robotics.\n\n* **Deep Learning (DL):** This is a subset of machine learning that uses artificial neural networks with multiple layers (hence \"deep\").  These networks are inspired by the structure and function of the human brain and are particularly good at handling complex, high-dimensional data like images and natural language.  Deep learning powers many advanced AI applications, such as image recognition, natural language processing, and speech recognition.\n\n* **Expert Systems:** These AI systems mimic the decision-making ability of a human expert in a specific domain.  They use a knowledge base of rules and facts to provide advice or make inferences.  They're less common now than ML and DL but still have applications in niche areas.\n\n**3. Models are the Result:**  The process of training an AI system results in a *model*. This model is a representation of the patterns and relationships learned from the data. It can be used to make predictions on new, unseen data.\n\n**4. Evaluation and Improvement:** AI models are constantly evaluated to assess their performance.  Metrics such as accuracy, precision, and recall are used to measure how well the model is doing.  Based on this evaluation, the model can be further refined and improved, often through a process called hyperparameter tuning or retraining with more data.\n\n\n**In Simple Terms:** Imagine teaching a dog a trick.  You (the trainer) provide data (the commands and treats), and the dog (the AI) learns through repetition and reward (the algorithm).  Over time, the dog develops a model (its learned behavior) that allows it to perform the trick reliably.  AI works similarly, but on a much larger and more complex scale, using sophisticated algorithms and vast amounts of data.\n\nIt's important to remember that AI is a constantly evolving field, and new techniques and approaches are continually being developed.  The explanation above covers some of the core concepts, but it's a simplified overview of a very complex subject.\n"
//	                    }
//	                ],
//	                "role": "model"
//	            },
//	            "finishReason": "STOP",
//	            "avgLogprobs": -0.2363232838580886
//	        }
//	    ],
//	    "usageMetadata": {
//	        "promptTokenCount": 4,
//	        "candidatesTokenCount": 747,
//	        "totalTokenCount": 751,
//	        "promptTokensDetails": [
//	            {
//	                "modality": "TEXT",
//	                "tokenCount": 4
//	            }
//	        ],
//	        "candidatesTokensDetails": [
//	            {
//	                "modality": "TEXT",
//	                "tokenCount": 747
//	            }
//	        ]
//	    },
//	    "modelVersion": "gemini-1.5-flash"
//	}
	
	private String extractResponseContent(String response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response);
			
			return rootNode.path("candidates")
					.get(0) //get(0) is accessing the 0th element of array
					.path("content")
					.path("parts")
					.get(0)
					.path("text")
					.asText();
		} catch (Exception e) {
			return "return processing request: " + e.getMessage();
		}

	}

	private String buildPrompt(EmailRequest emailRequest) {
		StringBuilder prompt = new StringBuilder();
		prompt.append("Generate a professional email reply with format for the following email content. Please do not generate a subject line. ");
		
		if(emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
			prompt.append("Use a ").append(emailRequest.getTone()).append(" tone");
		}
		prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
		
		return prompt.toString();
	}
}
