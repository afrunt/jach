/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.afrunt.jach;

import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.metadata.ACHMetadata;
import com.afrunt.jach.metadata.MetadataCollector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Andrii Frunt
 */
public class ACH {
    private MetadataCollector metadataCollector;
    private ACHUnmarshaller unmarshaller;
    private ACHMarshaller marshaller;

    public ACH() {
        metadataCollector = new MetadataCollector();
        unmarshaller = new ACHUnmarshaller(metadataCollector);
        marshaller = new ACHMarshaller(metadataCollector);
    }

    public ACHDocument read(InputStream is) {
        return unmarshaller.unmarshal(is);
    }

    public ACHDocument read(String string) {
        return unmarshaller.unmarshal(new ByteArrayInputStream(string.getBytes()));
    }

    public String write(ACHDocument document) {
        return marshaller.marshal(document);
    }

    public void write(ACHDocument document, OutputStream outputStream) {
        try {
            String str = marshaller.marshal(document);
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            throw new ACHException("Error writing ACH document to output stream", e);
        }

    }

    public ACHMetadata getMetadata() {
        return metadataCollector.collectMetadata();
    }

}
