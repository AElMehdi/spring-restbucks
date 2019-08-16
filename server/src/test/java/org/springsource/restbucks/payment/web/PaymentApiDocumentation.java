/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springsource.restbucks.payment.web;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.relaxedLinks;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springsource.restbucks.AbstractDocumentation;

/**
 * @author Oliver Gierke
 */

class PaymentApiDocumentation extends AbstractDocumentation {

	@Test
  void ordersResource() throws Exception {

		this.mockMvc.perform(get("/orders").accept(MediaTypes.HAL_JSON))//
				.andExpect(status().isOk())//
				.andDo(document("orders"))//
				.andDo(print());
	}

	@Test
	void accessOrderResource() throws Exception {

		this.mockMvc.perform(get("/orders/1").accept(MediaTypes.HAL_JSON))//
				.andExpect(status().isOk())//
				.andDo(document("order",
						relaxedLinks(linkWithRel("restbucks:payment")
								.description("In case the order is to be paid. Points to a payment resource.")),
						relaxedResponseFields(
								fieldWithPath("status").type(JsonFieldType.STRING)
										.description("A user readable description of the order's processing status."),
								fieldWithPath("lineItems").type(JsonFieldType.ARRAY)
										.description("The line items contained in the order."))));
	}
}
