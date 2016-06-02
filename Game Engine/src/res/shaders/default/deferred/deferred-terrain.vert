#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;
in vec3 inNormal;

out vec2 texCoords;
out vec3 normal;
out vec4 position;

uniform mat4 modelMatrix;
uniform mat4 mvp;

uniform sampler2D height;
uniform sampler2D normals;

uniform float size;

void main() {
	position = modelMatrix * vec4(inPosition, 1);

	texCoords = (position.xz / size + 1) / 2;

	position.y = texture(height, texCoords).r;

	gl_Position = mvp * vec4(inPosition.x, position.y, inPosition.z, 1);
	normal = texture(normals, vec2(texCoords.x, 1 - texCoords.y)).xyz;
}

