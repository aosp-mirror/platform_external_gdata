/*
** Copyright 2008, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** See the License for the specific language governing permissions and
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** limitations under the License.
*/
package com.google.wireless.gdata.client;

import com.google.wireless.gdata.GDataException;

/**
 * Exception thrown when a specified resource does not exist
 */
public class ResourceNotFoundException extends GDataException {

    /**
     * Creates a new ResourceNotFoundException.
     */
    public ResourceNotFoundException() {
    }

    /**
     * Creates a new ResourceNotFoundException with a supplied message.
     * @param message The message for the exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new ResourceNotFoundException with a supplied message and
     * underlying cause.
     *
     * @param message The message for the exception.
     * @param cause Another throwable that was caught and wrapped in this
     * exception.
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
