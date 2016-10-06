#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;
in vec3 inNormal;

out vec2 texCoords;
out vec4 position;
out vec3 normal;

uniform mat4 modelMatrix;
uniform mat4 mvp;

uniform sampler2D height;

uniform float size;
uniform float offset;

void main() {
	position = modelMatrix * vec4(inPosition, 1);

	texCoords = (position.xz / size + 1) / 2;

	vec2 transition = clamp((abs(inPosition.xz) - 0.3) * 5, 0, 1);

	vec2 offsetV = vec2(offset + offset * max(transition.x, transition.y), 0);

	float heightR = texture(height, texCoords + offsetV.xy).r;
	float heightL = texture(height, texCoords - offsetV.xy).r;
	float heightU = texture(height, texCoords + offsetV.yx).r;
	float heightD = texture(height, texCoords - offsetV.yx).r;

	normal = normalize(vec3(heightR - heightL, 2, heightD - heightU));

	position.y = texture(height, texCoords).r;

	gl_Position = mvp * vec4(inPosition.x, position.y, inPosition.z, 1);
}

