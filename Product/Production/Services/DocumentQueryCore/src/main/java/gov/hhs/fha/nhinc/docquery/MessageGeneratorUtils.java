/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.docquery;

import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.gateway.aggregator.document.DocumentConstants;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;

/**
 * @author akong
 * 
 */
public class MessageGeneratorUtils {

    private static MessageGeneratorUtils INSTANCE = new MessageGeneratorUtils();

    MessageGeneratorUtils() {
    };

    /**
     * Returns the singleton instance of this class.
     * 
     * @return the singleton instance
     */
    public static MessageGeneratorUtils getInstance() {
        return INSTANCE;
    }

    public NhinTargetSystemType convertFirstToNhinTargetSystemType(NhinTargetCommunitiesType targets) {
        NhinTargetSystemType nhinTargetSystem = new NhinTargetSystemType();

        if (targets != null && targets.getNhinTargetCommunity() != null && targets.getNhinTargetCommunity().size() > 0) {
            nhinTargetSystem.setHomeCommunity(targets.getNhinTargetCommunity().get(0).getHomeCommunity());
        }

        return nhinTargetSystem;

    }
    
    /**
     * Create a AdhocQueryResponse with severity set to error.
     * 
     * @param errorMsg - the code context value of the message
     * @param errorCode - the error code value of the message
     * @param status - the status of the message
     * @return the generated AdhocQueryResponse message
     */
    public AdhocQueryResponse createAdhocQueryErrorResponse(String errorMsg, String errorCode, String status) {
        RegistryErrorList regErrList = new RegistryErrorList();
        regErrList.setHighestSeverity(NhincConstants.XDS_REGISTRY_ERROR_SEVERITY_ERROR);

        RegistryError regErr = new RegistryError();
        regErrList.getRegistryError().add(regErr);
        regErr.setCodeContext(errorMsg);
        regErr.setErrorCode(errorCode);
        regErr.setSeverity(NhincConstants.XDS_REGISTRY_ERROR_SEVERITY_ERROR);

        AdhocQueryResponse response = new AdhocQueryResponse();
        response.setRegistryErrorList(regErrList);
        response.setStatus(status);

        return response;
    }

    /**
     * Creates a Policy failed error AdhocQueryResponse.
     * 
     * @return the generated AdhocQueryResponse message
     */
    public AdhocQueryResponse createPolicyErrorResponse() {
        return createAdhocQueryErrorResponse("Policy check failed.", DocumentConstants.XDS_ERRORCODE_REPOSITORY_ERROR,
                DocumentConstants.XDS_QUERY_RESPONSE_STATUS_FAILURE);
    }

    public AdhocQueryResponse createRepositoryErrorResponse(String errorMsg) {
        return createAdhocQueryErrorResponse(errorMsg, DocumentConstants.XDS_ERRORCODE_REPOSITORY_ERROR,
                DocumentConstants.XDS_QUERY_RESPONSE_STATUS_FAILURE);
    }

}
