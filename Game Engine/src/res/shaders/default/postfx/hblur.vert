#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;

uniform mat4 viewMatrix;
uniform float pixelSize;

out vec2 texCoord[11];

void main() {
	gl_Position = vec4(inPosition, 1);
	texCoord[0] = clamp(inTexCoords + vec2(-5 * pixelSize, 0), 0, 1);
	texCoord[1] = clamp(inTexCoords + vec2(-4 * pixelSize, 0), 0, 1);
	texCoord[2] = clamp(inTexCoords + vec2(-3 * pixelSize, 0), 0, 1);
	texCoord[3] = clamp(inTexCoords + vec2(-2 * pixelSize, 0), 0, 1);
	texCoord[4] = clamp(inTexCoords + vec2(-pixelSize, 0), 0, 1);
	texCoord[5] = inTexCoords;
	texCoord[6] = clamp(inTexCoords + vec2(pixelSize, 0), 0, 1);
	texCoord[7] = clamp(inTexCoords + vec2(2 * pixelSize, 0), 0, 1);
	texCoord[8] = clamp(inTexCoords + vec2(3 * pixelSize, 0), 0, 1);
	texCoord[9] = clamp(inTexCoords + vec2(4 * pixelSize, 0), 0, 1);
	texCoord[10] = clamp(inTexCoords + vec2(5 * pixelSize, 0), 0, 1);
}
