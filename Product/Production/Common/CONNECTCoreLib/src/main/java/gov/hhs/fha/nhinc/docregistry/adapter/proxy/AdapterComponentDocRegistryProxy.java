/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.docregistry.adapter.proxy;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

/**
 *
 * @author svalluripalli
 */
public interface AdapterComponentDocRegistryProxy
{

    /**
     * Performs a Registry Stored Query to get the Meta data from 
     * document registry for a document set
     * 
     * @param request
     * @return AdhocQueryResponse
     */
    public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest request);
    
}